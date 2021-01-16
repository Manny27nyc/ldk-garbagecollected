from bindingstypes import ConvInfo
from enum import Enum

def first_to_lower(string: str) -> str:
    first = string[0]
    return first.lower() + string[1:]


class Target(Enum):
    NODEJS = 1,
    BROWSER = 2

class Consts:
    def __init__(self, DEBUG: bool, target: Target, **kwargs):

        self.c_type_map = dict(
            uint8_t = ['number', 'Uint8Array'],
            uint16_t = ['number', 'Uint16Array'],
            uint32_t = ['number', 'Uint32Array'],
            long = ['number'],
        )

        self.to_hu_conv_templates = dict(
            ptr = 'const {var_name}_hu_conv: {human_type} = new {human_type}(null, {var_name});',
            default = 'const {var_name}_hu_conv: {human_type} = new {human_type}(null, {var_name});',
        )

        self.bindings_header = self.wasm_import_header(target) + """
export class VecOrSliceDef {
    public dataptr: number;
    public datalen: number;
    public stride: number;
    public constructor(dataptr: number, datalen: number, stride: number) {
        this.dataptr = dataptr; 
        this.datalen = datalen; 
        this.stride = stride;
    }
}

/*
TODO: load WASM file
static {
    System.loadLibrary(\"lightningjni\");
    init(java.lang.Enum.class, VecOrSliceDef.class);
    init_class_cache();
}

static native void init(java.lang.Class c, java.lang.Class slicedef);
static native void init_class_cache();

public static native boolean deref_bool(long ptr);
public static native long deref_long(long ptr);
public static native void free_heap_ptr(long ptr);
public static native byte[] read_bytes(long ptr, long len);
public static native byte[] get_u8_slice_bytes(long slice_ptr);
public static native long bytes_to_u8_vec(byte[] bytes);
public static native long new_txpointer_copy_data(byte[] txdata);
public static native void txpointer_free(long ptr);
public static native byte[] txpointer_get_buffer(long ptr);
public static native long vec_slice_len(long vec);
public static native long new_empty_slice_vec();
*/

"""

        self.common_base = """
            export default class CommonBase {
                ptr: number;
                ptrs_to: object[] = []; // new LinkedList(); TODO: build linked list implementation
                protected constructor(ptr: number) { this.ptr = ptr; }
                public _test_only_get_ptr(): number { return this.ptr; }
                protected finalize() {
                    // TODO: finalize myself
                }
            }
        """

        self.c_file_pfx = """#include <rust_types.h>
#include <stdatomic.h>
#include <lightning.h>

// These should be provided...somehow...
void *memset(void *s, int c, size_t n);
void *memcpy(void *dest, const void *src, size_t n);
int memcmp(const void *s1, const void *s2, size_t n);

void __attribute__((noreturn)) abort(void);
void assert(scalar expression);
"""

        if not DEBUG:
            self.c_file_pfx = self.c_file_pfx + """
void *malloc(size_t size);
void free(void *ptr);

#define MALLOC(a, _) malloc(a)
#define FREE(p) if ((long)(p) > 1024) { free(p); }
#define DO_ASSERT(a) (void)(a)
#define CHECK(a)
"""
        else:
            self.c_file_pfx = self.c_file_pfx + """
// Always run a, then assert it is true:
#define DO_ASSERT(a) do { bool _assert_val = (a); assert(_assert_val); } while(0)
// Assert a is true or do nothing
#define CHECK(a) DO_ASSERT(a)

// Running a leak check across all the allocations and frees of the JDK is a mess,
// so instead we implement our own naive leak checker here, relying on the -wrap
// linker option to wrap malloc/calloc/realloc/free, tracking everyhing allocated
// and free'd in Rust or C across the generated bindings shared library.

#define BT_MAX 128
typedef struct allocation {
	struct allocation* next;
	void* ptr;
	const char* struct_name;
} allocation;
static allocation* allocation_ll = NULL;

void* __real_malloc(size_t len);
void* __real_calloc(size_t nmemb, size_t len);
static void new_allocation(void* res, const char* struct_name) {
	allocation* new_alloc = __real_malloc(sizeof(allocation));
	new_alloc->ptr = res;
	new_alloc->struct_name = struct_name;
	new_alloc->next = allocation_ll;
	allocation_ll = new_alloc;
}
static void* MALLOC(size_t len, const char* struct_name) {
	void* res = __real_malloc(len);
	new_allocation(res, struct_name);
	return res;
}
void __real_free(void* ptr);
static void alloc_freed(void* ptr) {
	allocation* p = NULL;
	allocation* it = allocation_ll;
	while (it->ptr != ptr) {
		p = it; it = it->next;
		if (it == NULL) {
			//XXX: fprintf(stderr, "Tried to free unknown pointer %p\\n", ptr);
			return; // addrsan should catch malloc-unknown and print more info than we have
		}
	}
	if (p) { p->next = it->next; } else { allocation_ll = it->next; }
	DO_ASSERT(it->ptr == ptr);
	__real_free(it);
}
static void FREE(void* ptr) {
	if ((long)ptr < 1024) return; // Rust loves to create pointers to the NULL page for dummys
	alloc_freed(ptr);
	__real_free(ptr);
}

void* __wrap_malloc(size_t len) {
	void* res = __real_malloc(len);
	new_allocation(res, "malloc call");
	return res;
}
void* __wrap_calloc(size_t nmemb, size_t len) {
	void* res = __real_calloc(nmemb, len);
	new_allocation(res, "calloc call");
	return res;
}
void __wrap_free(void* ptr) {
	if (ptr == NULL) return;
	alloc_freed(ptr);
	__real_free(ptr);
}

void* __real_realloc(void* ptr, size_t newlen);
void* __wrap_realloc(void* ptr, size_t len) {
	if (ptr != NULL) alloc_freed(ptr);
	void* res = __real_realloc(ptr, len);
	new_allocation(res, "realloc call");
	return res;
}
void __wrap_reallocarray(void* ptr, size_t new_sz) {
	// Rust doesn't seem to use reallocarray currently
	DO_ASSERT(false);
}

void __attribute__((destructor)) check_leaks() {
	for (allocation* a = allocation_ll; a != NULL; a = a->next) {
		//XXX: fprintf(stderr, "%s %p remains\\n", a->struct_name, a->ptr);
	}
	DO_ASSERT(allocation_ll == NULL);
}
"""
        self.c_file_pfx = self.c_file_pfx + """
// We assume that CVec_u8Z and u8slice are the same size and layout (and thus pointers to the two can be mixed)
_Static_assert(sizeof(LDKCVec_u8Z) == sizeof(LDKu8slice), "Vec<u8> and [u8] need to have been mapped identically");
_Static_assert(offsetof(LDKCVec_u8Z, data) == offsetof(LDKu8slice, data), "Vec<u8> and [u8] need to have been mapped identically");
_Static_assert(offsetof(LDKCVec_u8Z, datalen) == offsetof(LDKu8slice, datalen), "Vec<u8> and [u8] need to have been mapped identically");

_Static_assert(sizeof(void*) == 4, "Pointers mut be 32 bits");

typedef struct int64_tArray {uint32_t len;int64_t *ptr;} int64_tArray;
typedef struct uint32_tArray {uint32_t len;int32_t *ptr;} uint32_tArray;
typedef struct int8_tArray {uint32_t len;int8_t *ptr;} int8_tArray;

typedef bool jboolean;

"""

        self.hu_struct_file_prefix = f"""
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

"""
        self.c_fn_ty_pfx = ""
        self.c_fn_name_pfx = ""
        self.c_fn_args_pfx = "void* ctx_TODO"
        self.file_ext = ".ts"
        self.ptr_c_ty = "uint32_t"
        self.ptr_native_ty = "uint32_t"
        self.result_c_ty = "uint32_t"
        self.ptr_arr = "uint32_tArray"
        self.get_native_arr_len_call = ("", ".len")
        self.get_native_arr_ptr_call = ("", ".ptr")

    def release_native_arr_ptr_call(self, arr_var, arr_ptr_var):
        return None
    def create_native_arr_call(self, arr_len, ty_info):
        if ty_info.c_ty == "int8_tArray":
            return "{ .len = " + arr_len + ", .ptr = MALLOC(" + arr_len + ", \"Native " + ty_info.c_ty + " Bytes\") }"
        elif ty_info.c_ty == "int64_tArray":
            return "{ .len = " + arr_len + ", .ptr = MALLOC(" + arr_len + " * sizeof(int64_t), \"Native " + ty_info.c_ty + " Bytes\") }"
        elif ty_info.c_ty == "uint32_tArray":
            return "{ .len = " + arr_len + ", .ptr = MALLOC(" + arr_len + " * sizeof(int32_t), \"Native " + ty_info.c_ty + " Bytes\") }"
        else:
            print("Need to create arr!", ty_info.c_ty)
            return ty_info.c_ty
    def set_native_arr_contents(self, arr_name, arr_len, ty_info):
        if ty_info.c_ty == "int8_tArray":
            return ("memcpy(" + arr_name + ".ptr, ", ", " + arr_len + ")")
        else:
            assert False
    def get_native_arr_contents(self, arr_name, dest_name, arr_len, ty_info, copy):
        if ty_info.c_ty == "int8_tArray":
            if copy:
                return "memcpy(" + dest_name + ", " + arr_name + ".ptr, " + arr_len + ")"
            else:
                return arr_name + ".ptr"
        else:
            return "(" + ty_info.subty.c_ty + "*) " + arr_name + ".ptr"
    def get_native_arr_elem(self, arr_name, idxc, ty_info):
        assert False # Only called if above is None
    def cleanup_native_arr_ref_contents(self, arr_name, dest_name, arr_len, ty_info):
        if ty_info.c_ty == "int8_tArray":
            return None
        else:
            return None


    def wasm_import_header(self, target):
        if target == Target.NODEJS:
            return """
const path = require('path').join(__dirname, 'bindings.wasm');
const bytes = require('fs').readFileSync(path);
let imports = {};
// add all exports to dictionary and move down?
// use `module.exports`?
// imports['./bindings.js'] = require('./bindings.js');

const wasmModule = new WebAssembly.Module(bytes);
const wasmInstance = new WebAssembly.Instance(wasmModule, imports);
// module.exports = wasmInstance.exports;
const wasm = wasmInstance.exports;
            """
        return ''

    def init_str(self, c_array_class_caches):
        return ""

    def native_c_unitary_enum_map(self, struct_name, variants):
        out_c = "static inline " + struct_name + " " + struct_name + "_from_js(int32_t ord) {\n"
        out_c = out_c + "\tswitch (ord) {\n"
        ord_v = 0

        out_typescript_enum_fields = ""

        for var in variants:
            out_c = out_c + "\t\tcase %d: return %s;\n" % (ord_v, var)
            ord_v = ord_v + 1
            out_typescript_enum_fields += f"{var},\n\t\t\t\t"
        out_c = out_c + "\t}\n"
        out_c = out_c + "\tabort();\n"
        out_c = out_c + "}\n"

        out_c = out_c + "static inline int32_t " + struct_name + "_to_js(" + struct_name + " val) {\n"
        out_c = out_c + "\tswitch (val) {\n"
        ord_v = 0
        for var in variants:
            out_c = out_c + "\t\tcase " + var + ": return %d;\n" % ord_v
            ord_v = ord_v + 1
        out_c = out_c + "\t\tdefault: abort();\n"
        out_c = out_c + "\t}\n"
        out_c = out_c + "}\n"

        out_typescript_enum = f"""
            export enum {struct_name} {{
                {out_typescript_enum_fields}
            }}
        """

        return (out_c, out_typescript_enum, "")

    def c_unitary_enum_to_native_call(self, ty_info):
        return (ty_info.rust_obj + "_to_js(", ")")
    def native_unitary_enum_to_c_call(self, ty_info):
        return (ty_info.rust_obj + "_from_js(", ")")

    def c_complex_enum_pass_ty(self, struct_name):
        return "uint32_t"

    def c_constr_native_complex_enum(self, struct_name, variant, c_params):
        ret = "0 /* " + struct_name + " - " + variant + " */"
        for param in c_params:
            ret = ret + "; (void) " + param
        return ret

    def native_c_map_trait(self, struct_name, field_var_conversions, field_function_lines):
        out_typescript_bindings = "\n\n\n// OUT_TYPESCRIPT_BINDINGS :: MAP_TRAIT :: START\n\n"

        constructor_arguments = ""
        super_instantiator = ""
        pointer_to_adder = ""
        impl_constructor_arguments = ""
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                constructor_arguments += f", {first_to_lower(var.arg_name)}?: {var.java_hu_ty}"
                impl_constructor_arguments += f", {var.arg_name}: {var.java_hu_ty}"
                if var.from_hu_conv is not None:
                    super_instantiator += ", " + var.from_hu_conv[0]
                    if var.from_hu_conv[1] != "":
                        pointer_to_adder += var.from_hu_conv[1] + ";\n"
                else:
                    super_instantiator += ", " + first_to_lower(var.arg_name)
            else:
                constructor_arguments += f", {first_to_lower(var[1])}?: bindings.{var[0]}"
                super_instantiator += ", " + first_to_lower(var[1])
                pointer_to_adder += "this.ptrs_to.push(" + first_to_lower(var[1]) + ");\n"
                impl_constructor_arguments += f", {first_to_lower(var[1])}_impl: {var[0].replace('LDK', '')}.{var[0].replace('LDK', '')}Interface"

        # BUILD INTERFACE METHODS
        out_java_interface = ""
        out_interface_implementation_overrides = ""
        java_methods = []
        for fn_line in field_function_lines:
            java_method_descriptor = ""
            if fn_line.fn_name != "free" and fn_line.fn_name != "clone":
                out_java_interface += fn_line.fn_name + "("
                out_interface_implementation_overrides += f"{fn_line.fn_name} ("

                for idx, arg_conv_info in enumerate(fn_line.args_ty):
                    if idx >= 1:
                        out_java_interface += ", "
                        out_interface_implementation_overrides += ", "
                    out_java_interface += f"{arg_conv_info.arg_name}: {arg_conv_info.java_hu_ty}"
                    out_interface_implementation_overrides += f"{arg_conv_info.arg_name}: {arg_conv_info.java_ty}"
                    java_method_descriptor += arg_conv_info.java_fn_ty_arg
                out_java_interface += f"): {fn_line.ret_ty_info.java_hu_ty};\n\t\t\t\t"
                java_method_descriptor += ")" + fn_line.ret_ty_info.java_fn_ty_arg
                java_methods.append((fn_line.fn_name, java_method_descriptor))

                out_interface_implementation_overrides += f"): {fn_line.ret_ty_info.java_ty} {{\n"

                interface_method_override_inset = "\t\t\t\t\t\t"
                interface_implementation_inset = "\t\t\t\t\t\t\t"
                for arg_info in fn_line.args_ty:
                    if arg_info.to_hu_conv is not None:
                        out_interface_implementation_overrides += interface_implementation_inset + arg_info.to_hu_conv.replace("\n", "\n\t\t\t\t") + "\n"

                if fn_line.ret_ty_info.java_ty != "void":
                    out_interface_implementation_overrides += interface_implementation_inset + fn_line.ret_ty_info.java_hu_ty + " ret = arg." + fn_line.fn_name + "("
                else:
                    out_interface_implementation_overrides += f"{interface_implementation_inset}arg." + fn_line.fn_name + "("

                for idx, arg_info in enumerate(fn_line.args_ty):
                    if idx != 0:
                        out_interface_implementation_overrides += ", "
                    if arg_info.to_hu_conv_name is not None:
                        out_interface_implementation_overrides += arg_info.to_hu_conv_name
                    else:
                        out_interface_implementation_overrides += arg_info.arg_name

                out_interface_implementation_overrides += ");\n"
                if fn_line.ret_ty_info.java_ty != "void":
                    if fn_line.ret_ty_info.from_hu_conv is not None:
                        out_interface_implementation_overrides = out_interface_implementation_overrides + "\t\t\t\t" + f"result: {fn_line.ret_ty_info.java_ty} = " + fn_line.ret_ty_info.from_hu_conv[0] + ";\n"
                        if fn_line.ret_ty_info.from_hu_conv[1] != "":
                            out_interface_implementation_overrides = out_interface_implementation_overrides + "\t\t\t\t" + fn_line.ret_ty_info.from_hu_conv[1].replace("this", "impl_holder.held") + ";\n"
                        #if fn_line.ret_ty_info.rust_obj in result_types:
                        # XXX: We need to handle this in conversion logic so that its cross-language!
                        # Avoid double-free by breaking the result - we should learn to clone these and then we can be safe instead
                        #    out_interface_implementation_overrides = out_interface_implementation_overrides + "\t\t\t\tret.ptr = 0;\n"
                        out_interface_implementation_overrides = out_interface_implementation_overrides + "\t\t\t\treturn result;\n"
                    else:
                        out_interface_implementation_overrides = out_interface_implementation_overrides + "\t\t\t\treturn ret;\n"
                out_interface_implementation_overrides += f"{interface_method_override_inset}}},\n\n{interface_method_override_inset}"

        trait_constructor_arguments = ""
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                trait_constructor_arguments += ", " + var.arg_name
            else:
                trait_constructor_arguments += ", " + var[1] + ".new_impl(" + var[1] + "_impl).bindings_instance"



        out_typescript_human = f"""
            {self.hu_struct_file_prefix}
            
            export class {struct_name.replace("LDK","")} extends CommonBase {{
            
                bindings_instance?: bindings.{struct_name};
                
                constructor(ptr?: number, arg?: bindings.{struct_name}{constructor_arguments}) {{
                    if (Number.isFinite(ptr)) {{
				        super(ptr);
				        this.bindings_instance = null;
				    }} else {{
				        // TODO: private constructor instantiation
				        super(bindings.{struct_name}_new(arg{super_instantiator}));
				        this.ptrs_to.push(arg);
				        {pointer_to_adder}
				    }}
                }}
                
                protected finalize() {{
                    if (this.ptr != 0) {{ 
                        bindings.{struct_name.replace("LDK","")}_free(this.ptr); 
                    }} 
                    super.finalize();
                }}
                
                static new_impl(arg: {struct_name.replace("LDK", "")}Interface{impl_constructor_arguments}): {struct_name.replace("LDK", "")} {{
                    const impl_holder: {struct_name}Holder = new {struct_name}Holder();
                    let structImplementation = <bindings.{struct_name}>{{
                    
                        // todo: in-line interface filling
                        
                        {out_interface_implementation_overrides}
                    }};
                    impl_holder.held = new {struct_name.replace("LDK", "")} (null, structImplementation{trait_constructor_arguments});
                }}
                
            }}
            
            export interface {struct_name.replace("LDK", "")}Interface {{
                {out_java_interface}
            }}
            
            class {struct_name}Holder {{
                held: {struct_name.replace("LDK", "")};
            }}
            
        """



        out_typescript_bindings += "\t\texport interface " + struct_name + " {\n"
        java_meths = []
        for fn_line in field_function_lines:
            if fn_line.fn_name != "free" and fn_line.fn_name != "clone":
                out_typescript_bindings += f"\t\t\t{fn_line.fn_name} ("

                for idx, arg_conv_info in enumerate(fn_line.args_ty):
                    if idx >= 1:
                        out_typescript_bindings = out_typescript_bindings + ", "
                    out_typescript_bindings += f"{arg_conv_info.arg_name}: {arg_conv_info.java_ty}"

                out_typescript_bindings += f"): {fn_line.ret_ty_info.java_ty};\n"

        out_typescript_bindings = out_typescript_bindings + "\t\t}\n\n"

        out_typescript_bindings += f"\t\texport function {struct_name}_new(impl: {struct_name}"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                out_typescript_bindings += f", {var.arg_name}: {var.java_ty}"
            else:
                out_typescript_bindings += f", {var[1]}: {var[0]}"

        out_typescript_bindings += f"""): number {{
            throw new Error('unimplemented'); // TODO: bind to WASM
        }}
        
        export function {struct_name}_get_obj_from_jcalls(val: number): {struct_name} {{
            throw new Error('unimplemented'); // TODO: bind to WASM
        }}
        """

        out_typescript_bindings += '\n// OUT_TYPESCRIPT_BINDINGS :: MAP_TRAIT :: END\n\n\n'

        # Now that we've written out our java code (and created java_meths), generate C
        out_c = "typedef struct " + struct_name + "_JCalls {\n"
        out_c = out_c + "\tatomic_size_t refcnt;\n"
        out_c = out_c + "\tJavaVM *vm;\n"
        out_c = out_c + "\tjweak o;\n"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                # We're a regular ol' field
                pass
            else:
                # We're a supertrait
                out_c = out_c + "\t" + var[0] + "_JCalls* " + var[1] + ";\n"
        for fn in field_function_lines:
            if fn.fn_name != "free" and fn.fn_name != "clone":
                out_c = out_c + "\tjmethodID " + fn.fn_name + "_meth;\n"
        out_c = out_c + "} " + struct_name + "_JCalls;\n"

        for fn_line in field_function_lines:
            if fn_line.fn_name == "free":
                out_c = out_c + "static void " + struct_name + "_JCalls_free(void* this_arg) {\n"
                out_c = out_c + "\t" + struct_name + "_JCalls *j_calls = (" + struct_name + "_JCalls*) this_arg;\n"
                out_c = out_c + "\tif (atomic_fetch_sub_explicit(&j_calls->refcnt, 1, memory_order_acquire) == 1) {\n"
                out_c = out_c + "\t\tJNIEnv *env;\n"
                out_c = out_c + "\t\tDO_ASSERT((*j_calls->vm)->GetEnv(j_calls->vm, (void**)&env, JNI_VERSION_1_8) == JNI_OK);\n"
                out_c = out_c + "\t\t(*env)->DeleteWeakGlobalRef(env, j_calls->o);\n"
                out_c = out_c + "\t\tFREE(j_calls);\n"
                out_c = out_c + "\t}\n}\n"

        for idx, fn_line in enumerate(field_function_lines):
            if fn_line.fn_name != "free" and fn_line.fn_name != "clone":
                assert fn_line.ret_ty_info.ty_info.get_full_rust_ty()[1] == ""
                out_c = out_c + fn_line.ret_ty_info.ty_info.get_full_rust_ty()[0] + " " + fn_line.fn_name + "_jcall("
                if fn_line.self_is_const:
                    out_c = out_c + "const void* this_arg"
                else:
                    out_c = out_c + "void* this_arg"

                for idx, arg in enumerate(fn_line.args_ty):
                    out_c = out_c + ", " + arg.ty_info.get_full_rust_ty()[0] + " " + arg.arg_name + arg.ty_info.get_full_rust_ty()[1]

                out_c = out_c + ") {\n"
                out_c = out_c + "\t" + struct_name + "_JCalls *j_calls = (" + struct_name + "_JCalls*) this_arg;\n"
                out_c = out_c + "\tJNIEnv *env;\n"
                out_c = out_c + "\tDO_ASSERT((*j_calls->vm)->GetEnv(j_calls->vm, (void**)&env, JNI_VERSION_1_8) == JNI_OK);\n"

                for arg_info in fn_line.args_ty:
                    if arg_info.ret_conv is not None:
                        out_c = out_c + "\t" + arg_info.ret_conv[0].replace('\n', '\n\t')
                        out_c = out_c + arg_info.arg_name
                        out_c = out_c + arg_info.ret_conv[1].replace('\n', '\n\t') + "\n"

                out_c = out_c + "\tjobject obj = (*env)->NewLocalRef(env, j_calls->o);\n\tCHECK(obj != NULL);\n"
                if fn_line.ret_ty_info.c_ty.endswith("Array"):
                    out_c = out_c + "\t" + fn_line.ret_ty_info.c_ty + " arg = (*env)->CallObjectMethod(env, obj, j_calls->" + fn_line.fn_name + "_meth"
                elif not fn_line.ret_ty_info.passed_as_ptr:
                    out_c = out_c + "\treturn (*env)->Call" + fn_line.ret_ty_info.java_ty.title() + "Method(env, obj, j_calls->" + fn_line.fn_name + "_meth"
                else:
                    out_c = out_c + "\t" + fn_line.ret_ty_info.rust_obj + "* ret = (" + fn_line.ret_ty_info.rust_obj + "*)(*env)->CallLongMethod(env, obj, j_calls->" + fn_line.fn_name + "_meth"

                for idx, arg_info in enumerate(fn_line.args_ty):
                    if arg_info.ret_conv is not None:
                        out_c = out_c + ", " + arg_info.ret_conv_name
                    else:
                        out_c = out_c + ", " + arg_info.arg_name
                out_c = out_c + ");\n"
                if fn_line.ret_ty_info.arg_conv is not None:
                    out_c = out_c + "\t" + fn_line.ret_ty_info.arg_conv.replace("\n", "\n\t") + "\n\treturn " + fn_line.ret_ty_info.arg_conv_name + ";\n"

                out_c = out_c + "}\n"

        # Write out a clone function whether we need one or not, as we use them in moving to rust
        out_c = out_c + "static void* " + struct_name + "_JCalls_clone(const void* this_arg) {\n"
        out_c = out_c + "\t" + struct_name + "_JCalls *j_calls = (" + struct_name + "_JCalls*) this_arg;\n"
        out_c = out_c + "\tatomic_fetch_add_explicit(&j_calls->refcnt, 1, memory_order_release);\n"
        for var in field_var_conversions:
            if not isinstance(var, ConvInfo):
                out_c = out_c + "\tatomic_fetch_add_explicit(&j_calls->" + var[1] + "->refcnt, 1, memory_order_release);\n"
        out_c = out_c + "\treturn (void*) this_arg;\n"
        out_c = out_c + "}\n"

        out_c = out_c + "static inline " + struct_name + " " + struct_name + "_init (" + self.c_fn_args_pfx + ", jobject o"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                out_c = out_c + ", " + var.c_ty + " " + var.arg_name
            else:
                out_c = out_c + ", jobject " + var[1]
        out_c = out_c + ") {\n"

        out_c = out_c + "\tjclass c = (*env)->GetObjectClass(env, o);\n"
        out_c = out_c + "\tCHECK(c != NULL);\n"
        out_c = out_c + "\t" + struct_name + "_JCalls *calls = MALLOC(sizeof(" + struct_name + "_JCalls), \"" + struct_name + "_JCalls\");\n"
        out_c = out_c + "\tatomic_init(&calls->refcnt, 1);\n"
        out_c = out_c + "\tDO_ASSERT((*env)->GetJavaVM(env, &calls->vm) == 0);\n"
        out_c = out_c + "\tcalls->o = (*env)->NewWeakGlobalRef(env, o);\n"

        for (fn_name, java_meth_descr) in java_meths:
            if fn_name != "free" and fn_name != "clone":
                out_c = out_c + "\tcalls->" + fn_name + "_meth = (*env)->GetMethodID(env, c, \"" + fn_name + "\", \"" + java_meth_descr + "\");\n"
                out_c = out_c + "\tCHECK(calls->" + fn_name + "_meth != NULL);\n"

        for var in field_var_conversions:
            if isinstance(var, ConvInfo) and var.arg_conv is not None:
                out_c = out_c + "\n\t" + var.arg_conv.replace("\n", "\n\t") +"\n"
        out_c = out_c + "\n\t" + struct_name + " ret = {\n"
        out_c = out_c + "\t\t.this_arg = (void*) calls,\n"
        for fn_line in field_function_lines:
            if fn_line.fn_name != "free" and fn_line.fn_name != "clone":
                out_c = out_c + "\t\t." + fn_line.fn_name + " = " + fn_line.fn_name + "_jcall,\n"
            elif fn_line.fn_name == "free":
                out_c = out_c + "\t\t.free = " + struct_name + "_JCalls_free,\n"
            else:
                out_c = out_c + "\t\t.clone = " + struct_name + "_JCalls_clone,\n"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                if var.arg_conv_name is not None:
                    out_c = out_c + "\t\t." + var.arg_name + " = " + var.arg_conv_name + ",\n"
                    out_c = out_c + "\t\t.set_" + var.arg_name + " = NULL,\n"
                else:
                    out_c = out_c + "\t\t." + var.var_name + " = " + var.var_name + ",\n"
                    out_c = out_c + "\t\t.set_" + var.var_name + " = NULL,\n"
            else:
                out_c = out_c + "\t\t." + var[1] + " = " + var[0] + "_init(env, clz, " + var[1] + "),\n"
        out_c = out_c + "\t};\n"
        for var in field_var_conversions:
            if not isinstance(var, ConvInfo):
                out_c = out_c + "\tcalls->" + var[1] + " = ret." + var[1] + ".this_arg;\n"
        out_c = out_c + "\treturn ret;\n"
        out_c = out_c + "}\n"

        out_c = out_c + self.c_fn_ty_pfx + "long " + self.c_fn_name_pfx + struct_name.replace("_", "_1") + "_1new (" + self.c_fn_args_pfx + ", jobject o"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                out_c = out_c + ", " + var.c_ty + " " + var.arg_name
            else:
                out_c = out_c + ", jobject " + var[1]
        out_c = out_c + ") {\n"
        out_c = out_c + "\t" + struct_name + " *res_ptr = MALLOC(sizeof(" + struct_name + "), \"" + struct_name + "\");\n"
        out_c = out_c + "\t*res_ptr = " + struct_name + "_init(env, clz, o"
        for var in field_var_conversions:
            if isinstance(var, ConvInfo):
                out_c = out_c + ", " + var.arg_name
            else:
                out_c = out_c + ", " + var[1]
        out_c = out_c + ");\n"
        out_c = out_c + "\treturn (long)res_ptr;\n"
        out_c = out_c + "}\n"

        out_c = out_c + self.c_fn_ty_pfx + "jobject " + self.c_fn_name_pfx + struct_name.replace("_", "_1") + "_1get_1obj_1from_1jcalls (" + self.c_fn_args_pfx + ", " + self.ptr_c_ty + " val) {\n"
        out_c = out_c + "\tjobject ret = (*env)->NewLocalRef(env, ((" + struct_name + "_JCalls*)val)->o);\n"
        out_c = out_c + "\tCHECK(ret != NULL);\n"
        out_c = out_c + "\treturn ret;\n"
        out_c = out_c + "}\n"


        return (out_typescript_bindings, out_typescript_human, out_c)

    def map_complex_enum(self, struct_name, variant_list, camel_to_snake):
        java_hu_type = struct_name.replace("LDK", "")

        out_java_enum = ""
        out_java = ""
        out_c = ""

        out_java_enum += (self.hu_struct_file_prefix)
        out_java_enum += ("export default class " + java_hu_type + " extends CommonBase {\n")
        out_java_enum += ("\tprotected constructor(_dummy: object, ptr: number) { super(ptr); }\n")
        out_java_enum += ("\tprotected finalize() {\n")
        out_java_enum += ("\t\tsuper.finalize();\n")
        out_java_enum += ("\t\tif (this.ptr != 0) { bindings." + java_hu_type + "_free(this.ptr); }\n")
        out_java_enum += ("\t}\n")
        out_java_enum += f"\tstatic constr_from_ptr(ptr: number): {java_hu_type} {{\n"
        out_java_enum += (f"\t\tconst raw_val: bindings.{struct_name} = bindings." + struct_name + "_ref_from_ptr(ptr);\n")
        java_hu_subclasses = ""

        out_java +=  ("\tpublic static class " + struct_name + " {\n")
        out_java +=  ("\t\tprivate " + struct_name + "() {}\n")
        for var in variant_list:
            out_java +=  ("\t\texport class " + var.var_name + " extends " + struct_name + " {\n")
            java_hu_subclasses = java_hu_subclasses + "export class " + var.var_name + " extends " + java_hu_type + " {\n"
            out_java_enum += ("\t\tif (raw_val instanceof bindings." + struct_name + "." + var.var_name + ") {\n")
            out_java_enum += ("\t\t\treturn new " + var.var_name + "(this.ptr, raw_val);\n")
            init_meth_params = ""
            init_meth_body = ""
            hu_conv_body = ""
            for idx, field_ty in enumerate(var.fields):
                out_java += ("\t\t\tpublic " + field_ty.java_ty + " " + field_ty.arg_name + ";\n")
                java_hu_subclasses = java_hu_subclasses + "\tpublic " + field_ty.arg_name + f": {field_ty.java_hu_ty};\n"
                if field_ty.to_hu_conv is not None:
                    hu_conv_body = hu_conv_body + "\t\tconst " + field_ty.arg_name + f": {field_ty.java_ty} = obj." + field_ty.arg_name + ";\n"
                    hu_conv_body = hu_conv_body + "\t\t" + field_ty.to_hu_conv.replace("\n", "\n\t\t\t") + "\n"
                    hu_conv_body = hu_conv_body + "\t\tthis." + field_ty.arg_name + " = " + field_ty.to_hu_conv_name + ";\n"
                else:
                    hu_conv_body = hu_conv_body + "\t\tthis." + field_ty.arg_name + " = obj." + field_ty.arg_name + ";\n"
                if idx > 0:
                    init_meth_params = init_meth_params + ", "
                init_meth_params = init_meth_params + field_ty.java_ty + " " + field_ty.arg_name
                init_meth_body = init_meth_body + "this." + field_ty.arg_name + " = " + field_ty.arg_name + "; "
            out_java +=  ("\t\t\t" + var.var_name + "(" + init_meth_params + ") { ")
            out_java +=  (init_meth_body)
            out_java +=  ("}\n")
            out_java += ("\t\t}\n")
            out_java_enum += ("\t\t}\n")
            java_hu_subclasses = java_hu_subclasses + "\tprivate constructor(ptr: number, obj: bindings." + struct_name + "." + var.var_name + ") {\n\t\tsuper(null, ptr);\n"
            java_hu_subclasses = java_hu_subclasses + hu_conv_body
            java_hu_subclasses = java_hu_subclasses + "\t}\n}\n"
        out_java += ("\t\tstatic native void init();\n")
        out_java += ("\t}\n")
        out_java_enum += ("\t\tthrow new Error('oops, this should be unreachable'); // Unreachable without extending the (internal) bindings interface\n\t}\n\n")
        out_java += ("\tstatic { " + struct_name + ".init(); }\n")
        out_java += ("\tpublic static native " + struct_name + " " + struct_name + "_ref_from_ptr(long ptr);\n");

        out_c += (self.c_fn_ty_pfx + self.c_complex_enum_pass_ty(struct_name) + " " + self.c_fn_name_pfx + struct_name.replace("_", "_1") + "_1ref_1from_1ptr (" + self.c_fn_args_pfx + ", " + self.ptr_c_ty + " ptr) {\n")
        out_c += ("\t" + struct_name + " *obj = (" + struct_name + "*)ptr;\n")
        out_c += ("\tswitch(obj->tag) {\n")
        for var in variant_list:
            out_c += ("\t\tcase " + struct_name + "_" + var.var_name + ": {\n")
            c_params = []
            for idx, field_map in enumerate(var.fields):
                if field_map.ret_conv is not None:
                    out_c += ("\t\t\t" + field_map.ret_conv[0].replace("\n", "\n\t\t\t"))
                    out_c += ("obj->" + camel_to_snake(var.var_name) + "." + field_map.arg_name)
                    out_c += (field_map.ret_conv[1].replace("\n", "\n\t\t\t") + "\n")
                    c_params.append(field_map.ret_conv_name)
                else:
                    c_params.append("obj->" + camel_to_snake(var.var_name) + "." + field_map.arg_name)
            out_c += ("\t\t\treturn " + self.c_constr_native_complex_enum(struct_name, var.var_name, c_params) + ";\n")
            out_c += ("\t\t}\n")
        out_c += ("\t\tdefault: abort();\n")
        out_c += ("\t}\n}\n")
        out_java_enum += ("}\n")
        out_java_enum += (java_hu_subclasses)
        return (out_java, out_java_enum, out_c)
