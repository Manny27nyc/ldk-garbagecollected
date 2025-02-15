package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;


/**
 * Channel parameters which apply to our counterparty. These are split out from [`ChannelDetails`]
 * to better separate parameters.
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class ChannelCounterparty extends CommonBase {
	ChannelCounterparty(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.ChannelCounterparty_free(ptr); }
	}

	/**
	 * The node_id of our counterparty
	 */
	public byte[] get_node_id() {
		byte[] ret = bindings.ChannelCounterparty_get_node_id(this.ptr);
		return ret;
	}

	/**
	 * The node_id of our counterparty
	 */
	public void set_node_id(byte[] val) {
		bindings.ChannelCounterparty_set_node_id(this.ptr, val);
	}

	/**
	 * The Features the channel counterparty provided upon last connection.
	 * Useful for routing as it is the most up-to-date copy of the counterparty's features and
	 * many routing-relevant features are present in the init context.
	 */
	public InitFeatures get_features() {
		long ret = bindings.ChannelCounterparty_get_features(this.ptr);
		if (ret < 1024) { return null; }
		InitFeatures ret_hu_conv = new InitFeatures(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * The Features the channel counterparty provided upon last connection.
	 * Useful for routing as it is the most up-to-date copy of the counterparty's features and
	 * many routing-relevant features are present in the init context.
	 */
	public void set_features(InitFeatures val) {
		bindings.ChannelCounterparty_set_features(this.ptr, val == null ? 0 : val.ptr & ~1);
		this.ptrs_to.add(val);
	}

	/**
	 * The value, in satoshis, that must always be held in the channel for our counterparty. This
	 * value ensures that if our counterparty broadcasts a revoked state, we can punish them by
	 * claiming at least this value on chain.
	 * 
	 * This value is not included in [`inbound_capacity_msat`] as it can never be spent.
	 * 
	 * [`inbound_capacity_msat`]: ChannelDetails::inbound_capacity_msat
	 */
	public long get_unspendable_punishment_reserve() {
		long ret = bindings.ChannelCounterparty_get_unspendable_punishment_reserve(this.ptr);
		return ret;
	}

	/**
	 * The value, in satoshis, that must always be held in the channel for our counterparty. This
	 * value ensures that if our counterparty broadcasts a revoked state, we can punish them by
	 * claiming at least this value on chain.
	 * 
	 * This value is not included in [`inbound_capacity_msat`] as it can never be spent.
	 * 
	 * [`inbound_capacity_msat`]: ChannelDetails::inbound_capacity_msat
	 */
	public void set_unspendable_punishment_reserve(long val) {
		bindings.ChannelCounterparty_set_unspendable_punishment_reserve(this.ptr, val);
	}

	/**
	 * Information on the fees and requirements that the counterparty requires when forwarding
	 * payments to us through this channel.
	 * 
	 * Note that the return value (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	@Nullable
	public CounterpartyForwardingInfo get_forwarding_info() {
		long ret = bindings.ChannelCounterparty_get_forwarding_info(this.ptr);
		if (ret < 1024) { return null; }
		CounterpartyForwardingInfo ret_hu_conv = new CounterpartyForwardingInfo(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Information on the fees and requirements that the counterparty requires when forwarding
	 * payments to us through this channel.
	 * 
	 * Note that val (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	public void set_forwarding_info(@Nullable CounterpartyForwardingInfo val) {
		bindings.ChannelCounterparty_set_forwarding_info(this.ptr, val == null ? 0 : val.ptr & ~1);
		this.ptrs_to.add(val);
	}

	/**
	 * Constructs a new ChannelCounterparty given each field
	 */
	public static ChannelCounterparty of(byte[] node_id_arg, InitFeatures features_arg, long unspendable_punishment_reserve_arg, CounterpartyForwardingInfo forwarding_info_arg) {
		long ret = bindings.ChannelCounterparty_new(node_id_arg, features_arg == null ? 0 : features_arg.ptr & ~1, unspendable_punishment_reserve_arg, forwarding_info_arg == null ? 0 : forwarding_info_arg.ptr & ~1);
		if (ret < 1024) { return null; }
		ChannelCounterparty ret_hu_conv = new ChannelCounterparty(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		ret_hu_conv.ptrs_to.add(features_arg);
		ret_hu_conv.ptrs_to.add(forwarding_info_arg);
		return ret_hu_conv;
	}

	/**
	 * Creates a copy of the ChannelCounterparty
	 */
	public ChannelCounterparty clone() {
		long ret = bindings.ChannelCounterparty_clone(this.ptr);
		if (ret < 1024) { return null; }
		ChannelCounterparty ret_hu_conv = new ChannelCounterparty(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

}
