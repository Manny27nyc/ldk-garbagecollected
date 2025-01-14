package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_ChannelTransactionParametersDecodeErrorZ extends CommonBase {
	private Result_ChannelTransactionParametersDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_ChannelTransactionParametersDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_ChannelTransactionParametersDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_ChannelTransactionParametersDecodeErrorZ_result_ok(ptr)) {
			return new Result_ChannelTransactionParametersDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_ChannelTransactionParametersDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_ChannelTransactionParametersDecodeErrorZ_OK extends Result_ChannelTransactionParametersDecodeErrorZ {
		public final ChannelTransactionParameters res;
		private Result_ChannelTransactionParametersDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_ChannelTransactionParametersDecodeErrorZ_get_ok(ptr);
			ChannelTransactionParameters res_hu_conv = new ChannelTransactionParameters(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_ChannelTransactionParametersDecodeErrorZ_Err extends Result_ChannelTransactionParametersDecodeErrorZ {
		public final DecodeError err;
		private Result_ChannelTransactionParametersDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_ChannelTransactionParametersDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_ChannelTransactionParametersDecodeErrorZ in the success state.
	 */
	public static Result_ChannelTransactionParametersDecodeErrorZ ok(ChannelTransactionParameters o) {
		long ret = bindings.CResult_ChannelTransactionParametersDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_ChannelTransactionParametersDecodeErrorZ ret_hu_conv = Result_ChannelTransactionParametersDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_ChannelTransactionParametersDecodeErrorZ in the error state.
	 */
	public static Result_ChannelTransactionParametersDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_ChannelTransactionParametersDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_ChannelTransactionParametersDecodeErrorZ ret_hu_conv = Result_ChannelTransactionParametersDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_ChannelTransactionParametersDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_ChannelTransactionParametersDecodeErrorZ clone() {
		long ret = bindings.CResult_ChannelTransactionParametersDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_ChannelTransactionParametersDecodeErrorZ ret_hu_conv = Result_ChannelTransactionParametersDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
