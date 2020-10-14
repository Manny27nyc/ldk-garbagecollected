package org.ldk.structs;

import org.ldk.impl.bindings;

import org.ldk.enums.*;

public class EventsProvider extends CommonBase {
	EventsProvider(Object _dummy, long ptr) { super(ptr); }
	public EventsProvider(bindings.LDKEventsProvider arg) {
		super(bindings.LDKEventsProvider_new(arg));
		this.ptrs_to.add(arg);
	}
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		bindings.EventsProvider_free(ptr); super.finalize();
	}

	// Skipped EventsProvider_get_and_clear_pending_events
}