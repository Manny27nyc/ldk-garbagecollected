package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import javax.annotation.Nullable;


/**
 * Details about a node in the network, known from the network announcement.
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class NodeInfo extends CommonBase {
	NodeInfo(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.NodeInfo_free(ptr); }
	}

	/**
	 * All valid channels a node has announced
	 */
	public void set_channels(long[] val) {
		bindings.NodeInfo_set_channels(this.ptr, val);
	}

	/**
	 * Lowest fees enabling routing via any of the enabled, known channels to a node.
	 * The two fields (flat and proportional fee) are independent,
	 * meaning they don't have to refer to the same channel.
	 * 
	 * Note that the return value (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	@Nullable
	public RoutingFees get_lowest_inbound_channel_fees() {
		long ret = bindings.NodeInfo_get_lowest_inbound_channel_fees(this.ptr);
		if (ret < 1024) { return null; }
		RoutingFees ret_hu_conv = new RoutingFees(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Lowest fees enabling routing via any of the enabled, known channels to a node.
	 * The two fields (flat and proportional fee) are independent,
	 * meaning they don't have to refer to the same channel.
	 * 
	 * Note that val (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	public void set_lowest_inbound_channel_fees(@Nullable RoutingFees val) {
		bindings.NodeInfo_set_lowest_inbound_channel_fees(this.ptr, val == null ? 0 : val.ptr & ~1);
		this.ptrs_to.add(val);
	}

	/**
	 * More information about a node from node_announcement.
	 * Optional because we store a Node entry after learning about it from
	 * a channel announcement, but before receiving a node announcement.
	 * 
	 * Note that the return value (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	@Nullable
	public NodeAnnouncementInfo get_announcement_info() {
		long ret = bindings.NodeInfo_get_announcement_info(this.ptr);
		if (ret < 1024) { return null; }
		NodeAnnouncementInfo ret_hu_conv = new NodeAnnouncementInfo(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * More information about a node from node_announcement.
	 * Optional because we store a Node entry after learning about it from
	 * a channel announcement, but before receiving a node announcement.
	 * 
	 * Note that val (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	public void set_announcement_info(@Nullable NodeAnnouncementInfo val) {
		bindings.NodeInfo_set_announcement_info(this.ptr, val == null ? 0 : val.ptr & ~1);
		this.ptrs_to.add(val);
	}

	/**
	 * Constructs a new NodeInfo given each field
	 */
	public static NodeInfo of(long[] channels_arg, RoutingFees lowest_inbound_channel_fees_arg, NodeAnnouncementInfo announcement_info_arg) {
		long ret = bindings.NodeInfo_new(channels_arg, lowest_inbound_channel_fees_arg == null ? 0 : lowest_inbound_channel_fees_arg.ptr & ~1, announcement_info_arg == null ? 0 : announcement_info_arg.ptr & ~1);
		if (ret < 1024) { return null; }
		NodeInfo ret_hu_conv = new NodeInfo(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		ret_hu_conv.ptrs_to.add(lowest_inbound_channel_fees_arg);
		ret_hu_conv.ptrs_to.add(announcement_info_arg);
		return ret_hu_conv;
	}

	/**
	 * Creates a copy of the NodeInfo
	 */
	public NodeInfo clone() {
		long ret = bindings.NodeInfo_clone(this.ptr);
		if (ret < 1024) { return null; }
		NodeInfo ret_hu_conv = new NodeInfo(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Serialize the NodeInfo object into a byte array which can be read by NodeInfo_read
	 */
	public byte[] write() {
		byte[] ret = bindings.NodeInfo_write(this.ptr);
		return ret;
	}

	/**
	 * Read a NodeInfo from a byte array, created by NodeInfo_write
	 */
	public static Result_NodeInfoDecodeErrorZ read(byte[] ser) {
		long ret = bindings.NodeInfo_read(ser);
		if (ret < 1024) { return null; }
		Result_NodeInfoDecodeErrorZ ret_hu_conv = Result_NodeInfoDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
