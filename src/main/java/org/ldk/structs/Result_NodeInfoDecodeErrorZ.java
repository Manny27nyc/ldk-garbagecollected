package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_NodeInfoDecodeErrorZ extends CommonBase {
	private Result_NodeInfoDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_NodeInfoDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_NodeInfoDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_NodeInfoDecodeErrorZ_result_ok(ptr)) {
			return new Result_NodeInfoDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_NodeInfoDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_NodeInfoDecodeErrorZ_OK extends Result_NodeInfoDecodeErrorZ {
		public final NodeInfo res;
		private Result_NodeInfoDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_NodeInfoDecodeErrorZ_get_ok(ptr);
			NodeInfo res_hu_conv = new NodeInfo(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_NodeInfoDecodeErrorZ_Err extends Result_NodeInfoDecodeErrorZ {
		public final DecodeError err;
		private Result_NodeInfoDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_NodeInfoDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_NodeInfoDecodeErrorZ in the success state.
	 */
	public static Result_NodeInfoDecodeErrorZ ok(NodeInfo o) {
		long ret = bindings.CResult_NodeInfoDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_NodeInfoDecodeErrorZ ret_hu_conv = Result_NodeInfoDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NodeInfoDecodeErrorZ in the error state.
	 */
	public static Result_NodeInfoDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_NodeInfoDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_NodeInfoDecodeErrorZ ret_hu_conv = Result_NodeInfoDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NodeInfoDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_NodeInfoDecodeErrorZ clone() {
		long ret = bindings.CResult_NodeInfoDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_NodeInfoDecodeErrorZ ret_hu_conv = Result_NodeInfoDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
