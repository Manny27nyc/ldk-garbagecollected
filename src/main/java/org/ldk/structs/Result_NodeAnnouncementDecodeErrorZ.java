package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Result_NodeAnnouncementDecodeErrorZ extends CommonBase {
	private Result_NodeAnnouncementDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_NodeAnnouncementDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_NodeAnnouncementDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_NodeAnnouncementDecodeErrorZ_result_ok(ptr)) {
			return new Result_NodeAnnouncementDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_NodeAnnouncementDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_NodeAnnouncementDecodeErrorZ_OK extends Result_NodeAnnouncementDecodeErrorZ {
		public final NodeAnnouncement res;
		private Result_NodeAnnouncementDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_NodeAnnouncementDecodeErrorZ_get_ok(ptr);
			NodeAnnouncement res_hu_conv = new NodeAnnouncement(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_NodeAnnouncementDecodeErrorZ_Err extends Result_NodeAnnouncementDecodeErrorZ {
		public final DecodeError err;
		private Result_NodeAnnouncementDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_NodeAnnouncementDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_NodeAnnouncementDecodeErrorZ in the success state.
	 */
	public static Result_NodeAnnouncementDecodeErrorZ ok(NodeAnnouncement o) {
		long ret = bindings.CResult_NodeAnnouncementDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_NodeAnnouncementDecodeErrorZ ret_hu_conv = Result_NodeAnnouncementDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NodeAnnouncementDecodeErrorZ in the error state.
	 */
	public static Result_NodeAnnouncementDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_NodeAnnouncementDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		if (ret < 1024) { return null; }
		Result_NodeAnnouncementDecodeErrorZ ret_hu_conv = Result_NodeAnnouncementDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_NodeAnnouncementDecodeErrorZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_NodeAnnouncementDecodeErrorZ clone() {
		long ret = bindings.CResult_NodeAnnouncementDecodeErrorZ_clone(this.ptr);
		if (ret < 1024) { return null; }
		Result_NodeAnnouncementDecodeErrorZ ret_hu_conv = Result_NodeAnnouncementDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
