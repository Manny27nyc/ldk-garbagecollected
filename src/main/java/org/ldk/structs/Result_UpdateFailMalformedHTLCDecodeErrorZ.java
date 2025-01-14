package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_UpdateFailMalformedHTLCDecodeErrorZ extends CommonBase {
	private Result_UpdateFailMalformedHTLCDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_UpdateFailMalformedHTLCDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_UpdateFailMalformedHTLCDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_UpdateFailMalformedHTLCDecodeErrorZ_result_ok(ptr)) {
			return new Result_UpdateFailMalformedHTLCDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_UpdateFailMalformedHTLCDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_UpdateFailMalformedHTLCDecodeErrorZ_OK extends Result_UpdateFailMalformedHTLCDecodeErrorZ {
		public final UpdateFailMalformedHTLC res;
		private Result_UpdateFailMalformedHTLCDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_UpdateFailMalformedHTLCDecodeErrorZ_get_ok(ptr);
			UpdateFailMalformedHTLC res_hu_conv = new UpdateFailMalformedHTLC(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_UpdateFailMalformedHTLCDecodeErrorZ_Err extends Result_UpdateFailMalformedHTLCDecodeErrorZ {
		public final DecodeError err;
		private Result_UpdateFailMalformedHTLCDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_UpdateFailMalformedHTLCDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_UpdateFailMalformedHTLCDecodeErrorZ in the success state.
	 */
	public static Result_UpdateFailMalformedHTLCDecodeErrorZ ok(UpdateFailMalformedHTLC o) {
		long ret = bindings.CResult_UpdateFailMalformedHTLCDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_UpdateFailMalformedHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailMalformedHTLCDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_UpdateFailMalformedHTLCDecodeErrorZ in the error state.
	 */
	public static Result_UpdateFailMalformedHTLCDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_UpdateFailMalformedHTLCDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_UpdateFailMalformedHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailMalformedHTLCDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_UpdateFailMalformedHTLCDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_UpdateFailMalformedHTLCDecodeErrorZ clone() {
		long ret = bindings.CResult_UpdateFailMalformedHTLCDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_UpdateFailMalformedHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailMalformedHTLCDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
