
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_PrivateRouteCreationErrorZ extends CommonBase {
	private Result_PrivateRouteCreationErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_PrivateRouteCreationErrorZ_free(ptr); } super.finalize();
	}

	static Result_PrivateRouteCreationErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_PrivateRouteCreationErrorZ_result_ok(ptr)) {
			return new Result_PrivateRouteCreationErrorZ_OK(null, ptr);
		} else {
			return new Result_PrivateRouteCreationErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_PrivateRouteCreationErrorZ_OK extends Result_PrivateRouteCreationErrorZ {
		public final PrivateRoute res;
		private Result_PrivateRouteCreationErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_PrivateRouteCreationErrorZ_get_ok(ptr);
			const res_hu_conv: PrivateRoute = new PrivateRoute(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_PrivateRouteCreationErrorZ_Err extends Result_PrivateRouteCreationErrorZ {
		public final CreationError err;
		private Result_PrivateRouteCreationErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			this.err = bindings.LDKCResult_PrivateRouteCreationErrorZ_get_err(ptr);
		}
	}

	public static Result_PrivateRouteCreationErrorZ constructor__ok(PrivateRoute o) {
		number ret = bindings.CResult_PrivateRouteCreationErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_PrivateRouteCreationErrorZ ret_hu_conv = Result_PrivateRouteCreationErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_PrivateRouteCreationErrorZ constructor__err(CreationError e) {
		number ret = bindings.CResult_PrivateRouteCreationErrorZ_err(e);
		Result_PrivateRouteCreationErrorZ ret_hu_conv = Result_PrivateRouteCreationErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public Result_PrivateRouteCreationErrorZ _clone() {
		number ret = bindings.CResult_PrivateRouteCreationErrorZ_clone(this.ptr);
		Result_PrivateRouteCreationErrorZ ret_hu_conv = Result_PrivateRouteCreationErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
