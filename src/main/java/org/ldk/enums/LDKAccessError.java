package org.ldk.enums;

/**
 * An error when accessing the chain via [`Access`].
 */
public enum LDKAccessError {
	LDKAccessError_UnknownChain,
	LDKAccessError_UnknownTx,
	; static native void init();
	static { init(); }
}