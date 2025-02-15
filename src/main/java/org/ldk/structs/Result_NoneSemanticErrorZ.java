package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_NoneSemanticErrorZ extends CommonBase {
	private Result_NoneSemanticErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_NoneSemanticErrorZ_free(ptr); } super.finalize();
	}

	static Result_NoneSemanticErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_NoneSemanticErrorZ_result_ok(ptr)) {
			return new Result_NoneSemanticErrorZ_OK(null, ptr);
		} else {
			return new Result_NoneSemanticErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_NoneSemanticErrorZ_OK extends Result_NoneSemanticErrorZ {
		private Result_NoneSemanticErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
		}
	}

	public static final class Result_NoneSemanticErrorZ_Err extends Result_NoneSemanticErrorZ {
		public final SemanticError err;
		private Result_NoneSemanticErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			this.err = bindings.LDKCResult_NoneSemanticErrorZ_get_err(ptr);
		}
	}

	/**
	 * Creates a new CResult_NoneSemanticErrorZ in the success state.
	 */
	public static Result_NoneSemanticErrorZ ok() {
		long ret = bindings.CResult_NoneSemanticErrorZ_ok();
		if (ret < 1024) { return null; }
		Result_NoneSemanticErrorZ ret_hu_conv = Result_NoneSemanticErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NoneSemanticErrorZ in the error state.
	 */
	public static Result_NoneSemanticErrorZ err(org.ldk.enums.SemanticError e) {
		long ret = bindings.CResult_NoneSemanticErrorZ_err(e);
		if (ret < 1024) { return null; }
		Result_NoneSemanticErrorZ ret_hu_conv = Result_NoneSemanticErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NoneSemanticErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_NoneSemanticErrorZ clone() {
		long ret = bindings.CResult_NoneSemanticErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_NoneSemanticErrorZ ret_hu_conv = Result_NoneSemanticErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
