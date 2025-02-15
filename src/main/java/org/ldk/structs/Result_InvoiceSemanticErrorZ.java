package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_InvoiceSemanticErrorZ extends CommonBase {
	private Result_InvoiceSemanticErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_InvoiceSemanticErrorZ_free(ptr); } super.finalize();
	}

	static Result_InvoiceSemanticErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_InvoiceSemanticErrorZ_result_ok(ptr)) {
			return new Result_InvoiceSemanticErrorZ_OK(null, ptr);
		} else {
			return new Result_InvoiceSemanticErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_InvoiceSemanticErrorZ_OK extends Result_InvoiceSemanticErrorZ {
		public final Invoice res;
		private Result_InvoiceSemanticErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_InvoiceSemanticErrorZ_get_ok(ptr);
			Invoice res_hu_conv = new Invoice(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_InvoiceSemanticErrorZ_Err extends Result_InvoiceSemanticErrorZ {
		public final SemanticError err;
		private Result_InvoiceSemanticErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			this.err = bindings.LDKCResult_InvoiceSemanticErrorZ_get_err(ptr);
		}
	}

	/**
	 * Creates a new CResult_InvoiceSemanticErrorZ in the success state.
	 */
	public static Result_InvoiceSemanticErrorZ ok(Invoice o) {
		long ret = bindings.CResult_InvoiceSemanticErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_InvoiceSemanticErrorZ ret_hu_conv = Result_InvoiceSemanticErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_InvoiceSemanticErrorZ in the error state.
	 */
	public static Result_InvoiceSemanticErrorZ err(org.ldk.enums.SemanticError e) {
		long ret = bindings.CResult_InvoiceSemanticErrorZ_err(e);
		if (ret < 1024) { return null; }
		Result_InvoiceSemanticErrorZ ret_hu_conv = Result_InvoiceSemanticErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_InvoiceSemanticErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_InvoiceSemanticErrorZ clone() {
		long ret = bindings.CResult_InvoiceSemanticErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_InvoiceSemanticErrorZ ret_hu_conv = Result_InvoiceSemanticErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
