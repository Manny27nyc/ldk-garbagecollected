package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;


/**
 * A script pubkey for shutting down a channel as defined by [BOLT #2].
 * 
 * [BOLT #2]: https://github.com/lightningnetwork/lightning-rfc/blob/master/02-peer-protocol.md
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class ShutdownScript extends CommonBase {
	ShutdownScript(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.ShutdownScript_free(ptr); }
	}

	/**
	 * Creates a copy of the ShutdownScript
	 */
	public ShutdownScript clone() {
		long ret = bindings.ShutdownScript_clone(this.ptr);
		if (ret < 1024) { return null; }
		ShutdownScript ret_hu_conv = new ShutdownScript(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Serialize the ShutdownScript object into a byte array which can be read by ShutdownScript_read
	 */
	public byte[] write() {
		byte[] ret = bindings.ShutdownScript_write(this.ptr);
		return ret;
	}

	/**
	 * Read a ShutdownScript from a byte array, created by ShutdownScript_write
	 */
	public static Result_ShutdownScriptDecodeErrorZ read(byte[] ser) {
		long ret = bindings.ShutdownScript_read(ser);
		if (ret < 1024) { return null; }
		Result_ShutdownScriptDecodeErrorZ ret_hu_conv = Result_ShutdownScriptDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Generates a P2PKH script pubkey from the given [`PubkeyHash`].
	 */
	public static ShutdownScript new_p2pkh(byte[] pubkey_hash) {
		long ret = bindings.ShutdownScript_new_p2pkh(pubkey_hash);
		if (ret < 1024) { return null; }
		ShutdownScript ret_hu_conv = new ShutdownScript(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Generates a P2SH script pubkey from the given [`ScriptHash`].
	 */
	public static ShutdownScript new_p2sh(byte[] script_hash) {
		long ret = bindings.ShutdownScript_new_p2sh(script_hash);
		if (ret < 1024) { return null; }
		ShutdownScript ret_hu_conv = new ShutdownScript(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Generates a P2WPKH script pubkey from the given [`WPubkeyHash`].
	 */
	public static ShutdownScript new_p2wpkh(byte[] pubkey_hash) {
		long ret = bindings.ShutdownScript_new_p2wpkh(pubkey_hash);
		if (ret < 1024) { return null; }
		ShutdownScript ret_hu_conv = new ShutdownScript(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Generates a P2WSH script pubkey from the given [`WScriptHash`].
	 */
	public static ShutdownScript new_p2wsh(byte[] script_hash) {
		long ret = bindings.ShutdownScript_new_p2wsh(script_hash);
		if (ret < 1024) { return null; }
		ShutdownScript ret_hu_conv = new ShutdownScript(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Generates a witness script pubkey from the given segwit version and program.
	 * 
	 * Note for version-zero witness scripts you must use [`ShutdownScript::new_p2wpkh`] or
	 * [`ShutdownScript::new_p2wsh`] instead.
	 * 
	 * # Errors
	 * 
	 * This function may return an error if `program` is invalid for the segwit `version`.
	 */
	public static Result_ShutdownScriptInvalidShutdownScriptZ new_witness_program(byte version, byte[] program) {
		long ret = bindings.ShutdownScript_new_witness_program(version, program);
		if (ret < 1024) { return null; }
		Result_ShutdownScriptInvalidShutdownScriptZ ret_hu_conv = Result_ShutdownScriptInvalidShutdownScriptZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Converts the shutdown script into the underlying [`Script`].
	 */
	public byte[] into_inner() {
		byte[] ret = bindings.ShutdownScript_into_inner(this.ptr);
		this.ptrs_to.add(this);
		return ret;
	}

	/**
	 * Returns the [`PublicKey`] used for a P2WPKH shutdown script if constructed directly from it.
	 * 
	 * Note that the return value (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	@Nullable
	public byte[] as_legacy_pubkey() {
		byte[] ret = bindings.ShutdownScript_as_legacy_pubkey(this.ptr);
		return ret;
	}

	/**
	 * Returns whether the shutdown script is compatible with the features as defined by BOLT #2.
	 * 
	 * Specifically, checks for compliance with feature `option_shutdown_anysegwit`.
	 */
	public boolean is_compatible(InitFeatures features) {
		boolean ret = bindings.ShutdownScript_is_compatible(this.ptr, features == null ? 0 : features.ptr & ~1);
		this.ptrs_to.add(features);
		return ret;
	}

}
