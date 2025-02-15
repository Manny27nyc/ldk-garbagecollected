package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;


/**
 * An address which can be used to connect to a remote peer
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class NetAddress extends CommonBase {
	private NetAddress(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.NetAddress_free(ptr); }
	}
	static NetAddress constr_from_ptr(long ptr) {
		bindings.LDKNetAddress raw_val = bindings.LDKNetAddress_ref_from_ptr(ptr);
		if (raw_val.getClass() == bindings.LDKNetAddress.IPv4.class) {
			return new IPv4(ptr, (bindings.LDKNetAddress.IPv4)raw_val);
		}
		if (raw_val.getClass() == bindings.LDKNetAddress.IPv6.class) {
			return new IPv6(ptr, (bindings.LDKNetAddress.IPv6)raw_val);
		}
		if (raw_val.getClass() == bindings.LDKNetAddress.OnionV2.class) {
			return new OnionV2(ptr, (bindings.LDKNetAddress.OnionV2)raw_val);
		}
		if (raw_val.getClass() == bindings.LDKNetAddress.OnionV3.class) {
			return new OnionV3(ptr, (bindings.LDKNetAddress.OnionV3)raw_val);
		}
		assert false; return null; // Unreachable without extending the (internal) bindings interface
	}

	public final static class IPv4 extends NetAddress {
		/**
		 * The 4-byte IPv4 address
		*/
		public final byte[] addr;
		/**
		 * The port on which the node is listening
		*/
		public final short port;
		private IPv4(long ptr, bindings.LDKNetAddress.IPv4 obj) {
			super(null, ptr);
			this.addr = obj.addr;
			this.port = obj.port;
		}
	}
	public final static class IPv6 extends NetAddress {
		/**
		 * The 16-byte IPv6 address
		*/
		public final byte[] addr;
		/**
		 * The port on which the node is listening
		*/
		public final short port;
		private IPv6(long ptr, bindings.LDKNetAddress.IPv6 obj) {
			super(null, ptr);
			this.addr = obj.addr;
			this.port = obj.port;
		}
	}
	public final static class OnionV2 extends NetAddress {
		/**
		 * The bytes (usually encoded in base32 with \".onion\" appended)
		*/
		public final byte[] addr;
		/**
		 * The port on which the node is listening
		*/
		public final short port;
		private OnionV2(long ptr, bindings.LDKNetAddress.OnionV2 obj) {
			super(null, ptr);
			this.addr = obj.addr;
			this.port = obj.port;
		}
	}
	public final static class OnionV3 extends NetAddress {
		/**
		 * The ed25519 long-term public key of the peer
		*/
		public final byte[] ed25519_pubkey;
		/**
		 * The checksum of the pubkey and version, as included in the onion address
		*/
		public final short checksum;
		/**
		 * The version byte, as defined by the Tor Onion v3 spec.
		*/
		public final byte version;
		/**
		 * The port on which the node is listening
		*/
		public final short port;
		private OnionV3(long ptr, bindings.LDKNetAddress.OnionV3 obj) {
			super(null, ptr);
			this.ed25519_pubkey = obj.ed25519_pubkey;
			this.checksum = obj.checksum;
			this.version = obj.version;
			this.port = obj.port;
		}
	}
	/**
	 * Creates a copy of the NetAddress
	 */
	public NetAddress clone() {
		long ret = bindings.NetAddress_clone(this.ptr);
		if (ret < 1024) { return null; }
		NetAddress ret_hu_conv = NetAddress.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Utility method to constructs a new IPv4-variant NetAddress
	 */
	public static NetAddress ipv4(byte[] addr, short port) {
		long ret = bindings.NetAddress_ipv4(addr, port);
		if (ret < 1024) { return null; }
		NetAddress ret_hu_conv = NetAddress.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Utility method to constructs a new IPv6-variant NetAddress
	 */
	public static NetAddress ipv6(byte[] addr, short port) {
		long ret = bindings.NetAddress_ipv6(addr, port);
		if (ret < 1024) { return null; }
		NetAddress ret_hu_conv = NetAddress.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Utility method to constructs a new OnionV2-variant NetAddress
	 */
	public static NetAddress onion_v2(byte[] addr, short port) {
		long ret = bindings.NetAddress_onion_v2(addr, port);
		if (ret < 1024) { return null; }
		NetAddress ret_hu_conv = NetAddress.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Utility method to constructs a new OnionV3-variant NetAddress
	 */
	public static NetAddress onion_v3(byte[] ed25519_pubkey, short checksum, byte version, short port) {
		long ret = bindings.NetAddress_onion_v3(ed25519_pubkey, checksum, version, port);
		if (ret < 1024) { return null; }
		NetAddress ret_hu_conv = NetAddress.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Serialize the NetAddress object into a byte array which can be read by NetAddress_read
	 */
	public byte[] write() {
		byte[] ret = bindings.NetAddress_write(this.ptr);
		return ret;
	}

	/**
	 * Read a NetAddress from a byte array, created by NetAddress_write
	 */
	public static Result_NetAddressDecodeErrorZ read(byte[] ser) {
		long ret = bindings.NetAddress_read(ser);
		if (ret < 1024) { return null; }
		Result_NetAddressDecodeErrorZ ret_hu_conv = Result_NetAddressDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
