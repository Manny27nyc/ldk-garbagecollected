package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;

public class Pong extends CommonBase {
	Pong(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		bindings.Pong_free(ptr);
	}

	public Pong(Pong orig) {
		super(bindings.Pong_clone(orig == null ? 0 : orig.ptr & ~1));
		this.ptrs_to.add(orig);
	}

	public short get_byteslen(Pong this_ptr) {
		short ret = bindings.Pong_get_byteslen(this_ptr == null ? 0 : this_ptr.ptr & ~1);
		this.ptrs_to.add(this_ptr);
		return ret;
	}

	public void set_byteslen(Pong this_ptr, short val) {
		bindings.Pong_set_byteslen(this_ptr == null ? 0 : this_ptr.ptr & ~1, val);
		this.ptrs_to.add(this_ptr);
	}

	public Pong(short byteslen_arg) {
		super(bindings.Pong_new(byteslen_arg));
	}

	public byte[] write(Pong obj) {
		byte[] ret = bindings.Pong_write(obj == null ? 0 : obj.ptr & ~1);
		this.ptrs_to.add(obj);
		return ret;
	}

	public Pong(byte[] ser) {
		super(bindings.Pong_read(ser));
	}

}
