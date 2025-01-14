package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_DescriptionCreationErrorZ extends CommonBase {
	private Result_DescriptionCreationErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_DescriptionCreationErrorZ_free(ptr); } super.finalize();
	}

	static Result_DescriptionCreationErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_DescriptionCreationErrorZ_result_ok(ptr)) {
			return new Result_DescriptionCreationErrorZ_OK(null, ptr);
		} else {
			return new Result_DescriptionCreationErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_DescriptionCreationErrorZ_OK extends Result_DescriptionCreationErrorZ {
		public final Description res;
		private Result_DescriptionCreationErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_DescriptionCreationErrorZ_get_ok(ptr);
			Description res_hu_conv = new Description(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_DescriptionCreationErrorZ_Err extends Result_DescriptionCreationErrorZ {
		public final CreationError err;
		private Result_DescriptionCreationErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			this.err = bindings.LDKCResult_DescriptionCreationErrorZ_get_err(ptr);
		}
	}

	/**
	 * Creates a new CResult_DescriptionCreationErrorZ in the success state.
	 */
	public static Result_DescriptionCreationErrorZ ok(Description o) {
		long ret = bindings.CResult_DescriptionCreationErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_DescriptionCreationErrorZ ret_hu_conv = Result_DescriptionCreationErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_DescriptionCreationErrorZ in the error state.
	 */
	public static Result_DescriptionCreationErrorZ err(org.ldk.enums.CreationError e) {
		long ret = bindings.CResult_DescriptionCreationErrorZ_err(e);
		if (ret < 1024) { return null; }
		Result_DescriptionCreationErrorZ ret_hu_conv = Result_DescriptionCreationErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_DescriptionCreationErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_DescriptionCreationErrorZ clone() {
		long ret = bindings.CResult_DescriptionCreationErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_DescriptionCreationErrorZ ret_hu_conv = Result_DescriptionCreationErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
