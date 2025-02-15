
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_ClosingSignedFeeRangeDecodeErrorZ extends CommonBase {
	private Result_ClosingSignedFeeRangeDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_ClosingSignedFeeRangeDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_ClosingSignedFeeRangeDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_ClosingSignedFeeRangeDecodeErrorZ_result_ok(ptr)) {
			return new Result_ClosingSignedFeeRangeDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_ClosingSignedFeeRangeDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_ClosingSignedFeeRangeDecodeErrorZ_OK extends Result_ClosingSignedFeeRangeDecodeErrorZ {
		public final ClosingSignedFeeRange res;
		private Result_ClosingSignedFeeRangeDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_ClosingSignedFeeRangeDecodeErrorZ_get_ok(ptr);
			const res_hu_conv: ClosingSignedFeeRange = new ClosingSignedFeeRange(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_ClosingSignedFeeRangeDecodeErrorZ_Err extends Result_ClosingSignedFeeRangeDecodeErrorZ {
		public final DecodeError err;
		private Result_ClosingSignedFeeRangeDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number err = bindings.LDKCResult_ClosingSignedFeeRangeDecodeErrorZ_get_err(ptr);
			const err_hu_conv: DecodeError = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	public static Result_ClosingSignedFeeRangeDecodeErrorZ constructor__ok(ClosingSignedFeeRange o) {
		number ret = bindings.CResult_ClosingSignedFeeRangeDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_ClosingSignedFeeRangeDecodeErrorZ ret_hu_conv = Result_ClosingSignedFeeRangeDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_ClosingSignedFeeRangeDecodeErrorZ constructor__err(DecodeError e) {
		number ret = bindings.CResult_ClosingSignedFeeRangeDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_ClosingSignedFeeRangeDecodeErrorZ ret_hu_conv = Result_ClosingSignedFeeRangeDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	public Result_ClosingSignedFeeRangeDecodeErrorZ _clone() {
		number ret = bindings.CResult_ClosingSignedFeeRangeDecodeErrorZ_clone(this.ptr);
		Result_ClosingSignedFeeRangeDecodeErrorZ ret_hu_conv = Result_ClosingSignedFeeRangeDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
