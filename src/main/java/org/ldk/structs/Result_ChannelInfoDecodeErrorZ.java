package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_ChannelInfoDecodeErrorZ extends CommonBase {
	private Result_ChannelInfoDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_ChannelInfoDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_ChannelInfoDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_ChannelInfoDecodeErrorZ_result_ok(ptr)) {
			return new Result_ChannelInfoDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_ChannelInfoDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_ChannelInfoDecodeErrorZ_OK extends Result_ChannelInfoDecodeErrorZ {
		public final ChannelInfo res;
		private Result_ChannelInfoDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_ChannelInfoDecodeErrorZ_get_ok(ptr);
			ChannelInfo res_hu_conv = new ChannelInfo(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_ChannelInfoDecodeErrorZ_Err extends Result_ChannelInfoDecodeErrorZ {
		public final DecodeError err;
		private Result_ChannelInfoDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_ChannelInfoDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_ChannelInfoDecodeErrorZ in the success state.
	 */
	public static Result_ChannelInfoDecodeErrorZ ok(ChannelInfo o) {
		long ret = bindings.CResult_ChannelInfoDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_ChannelInfoDecodeErrorZ ret_hu_conv = Result_ChannelInfoDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_ChannelInfoDecodeErrorZ in the error state.
	 */
	public static Result_ChannelInfoDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_ChannelInfoDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_ChannelInfoDecodeErrorZ ret_hu_conv = Result_ChannelInfoDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_ChannelInfoDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_ChannelInfoDecodeErrorZ clone() {
		long ret = bindings.CResult_ChannelInfoDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_ChannelInfoDecodeErrorZ ret_hu_conv = Result_ChannelInfoDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
