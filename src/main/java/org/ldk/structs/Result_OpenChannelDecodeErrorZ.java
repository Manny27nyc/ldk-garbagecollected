package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_OpenChannelDecodeErrorZ extends CommonBase {
	private Result_OpenChannelDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_OpenChannelDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_OpenChannelDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_OpenChannelDecodeErrorZ_result_ok(ptr)) {
			return new Result_OpenChannelDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_OpenChannelDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_OpenChannelDecodeErrorZ_OK extends Result_OpenChannelDecodeErrorZ {
		public final OpenChannel res;
		private Result_OpenChannelDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_OpenChannelDecodeErrorZ_get_ok(ptr);
			OpenChannel res_hu_conv = new OpenChannel(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_OpenChannelDecodeErrorZ_Err extends Result_OpenChannelDecodeErrorZ {
		public final DecodeError err;
		private Result_OpenChannelDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_OpenChannelDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_OpenChannelDecodeErrorZ in the success state.
	 */
	public static Result_OpenChannelDecodeErrorZ ok(OpenChannel o) {
		long ret = bindings.CResult_OpenChannelDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_OpenChannelDecodeErrorZ in the error state.
	 */
	public static Result_OpenChannelDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_OpenChannelDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_OpenChannelDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_OpenChannelDecodeErrorZ clone() {
		long ret = bindings.CResult_OpenChannelDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
