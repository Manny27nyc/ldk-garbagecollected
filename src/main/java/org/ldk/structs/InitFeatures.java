package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;


/**
 * Features used within an `init` message.
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class InitFeatures extends CommonBase {
	InitFeatures(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.InitFeatures_free(ptr); }
	}

	/**
	 * Checks if two InitFeaturess contain equal inner contents.
	 * This ignores pointers and is_owned flags and looks at the values in fields.
	 * Two objects with NULL inner values will be considered "equal" here.
	 */
	public boolean eq(InitFeatures b) {
		boolean ret = bindings.InitFeatures_eq(this.ptr, b == null ? 0 : b.ptr & ~1);
		this.ptrs_to.add(b);
		return ret;
	}

	/**
	 * Creates a copy of the InitFeatures
	 */
	public InitFeatures clone() {
		long ret = bindings.InitFeatures_clone(this.ptr);
		if (ret < 1024) { return null; }
		InitFeatures ret_hu_conv = new InitFeatures(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Create a blank Features with no features set
	 */
	public static InitFeatures empty() {
		long ret = bindings.InitFeatures_empty();
		if (ret < 1024) { return null; }
		InitFeatures ret_hu_conv = new InitFeatures(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Creates a Features with the bits set which are known by the implementation
	 */
	public static InitFeatures known() {
		long ret = bindings.InitFeatures_known();
		if (ret < 1024) { return null; }
		InitFeatures ret_hu_conv = new InitFeatures(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Returns true if this `Features` object contains unknown feature flags which are set as
	 * \"required\".
	 */
	public boolean requires_unknown_bits() {
		boolean ret = bindings.InitFeatures_requires_unknown_bits(this.ptr);
		return ret;
	}

	/**
	 * Returns whether the `payment_secret` feature is supported.
	 */
	public boolean supports_payment_secret() {
		boolean ret = bindings.InitFeatures_supports_payment_secret(this.ptr);
		return ret;
	}

	/**
	 * Serialize the InitFeatures object into a byte array which can be read by InitFeatures_read
	 */
	public byte[] write() {
		byte[] ret = bindings.InitFeatures_write(this.ptr);
		return ret;
	}

	/**
	 * Read a InitFeatures from a byte array, created by InitFeatures_write
	 */
	public static Result_InitFeaturesDecodeErrorZ read(byte[] ser) {
		long ret = bindings.InitFeatures_read(ser);
		if (ret < 1024) { return null; }
		Result_InitFeaturesDecodeErrorZ ret_hu_conv = Result_InitFeaturesDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
