
            
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location



            export default class ChainMonitor extends CommonBase {
                constructor(_dummy: object, ptr: number) {
                    super(ptr);
                }

                
                protected finalize() {
                    super.finalize();

                    if (this.ptr != 0) {
                        bindings.ChainMonitor_free(this.ptr);
                    }
                }
	public static ChainMonitor constructor_new(Option_FilterZ chain_source, BroadcasterInterface broadcaster, Logger logger, FeeEstimator feeest, Persist persister) {
		number ret = bindings.ChainMonitor_new(chain_source.ptr, broadcaster == null ? 0 : broadcaster.ptr, logger == null ? 0 : logger.ptr, feeest == null ? 0 : feeest.ptr, persister == null ? 0 : persister.ptr);
		const ret_hu_conv: ChainMonitor = new ChainMonitor(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		ret_hu_conv.ptrs_to.add(broadcaster);
		ret_hu_conv.ptrs_to.add(logger);
		ret_hu_conv.ptrs_to.add(feeest);
		ret_hu_conv.ptrs_to.add(persister);
		return ret_hu_conv;
	}

	public Balance[] get_claimable_balances(ChannelDetails[] ignored_channels) {
		number[] ret = bindings.ChainMonitor_get_claimable_balances(this.ptr, ignored_channels != null ? Arrays.stream(ignored_channels).map(ignored_channels_conv_16 -> ignored_channels_conv_16 == null ? 0 : ignored_channels_conv_16.ptr & ~1).toArray(number[]::new) : null);
		Balance[] ret_conv_9_arr = new Balance[ret.length];
		for (int j = 0; j < ret.length; j++) {
			number ret_conv_9 = ret[j];
			Balance ret_conv_9_hu_conv = Balance.constr_from_ptr(ret_conv_9);
			ret_conv_9_hu_conv.ptrs_to.add(this);
			ret_conv_9_arr[j] = ret_conv_9_hu_conv;
		}
		for (ChannelDetails ignored_channels_conv_16: ignored_channels) { this.ptrs_to.add(ignored_channels_conv_16); };
		return ret_conv_9_arr;
	}

	public Listen as_Listen() {
		number ret = bindings.ChainMonitor_as_Listen(this.ptr);
		Listen ret_hu_conv = new Listen(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	public Confirm as_Confirm() {
		number ret = bindings.ChainMonitor_as_Confirm(this.ptr);
		Confirm ret_hu_conv = new Confirm(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	public Watch as_Watch() {
		number ret = bindings.ChainMonitor_as_Watch(this.ptr);
		Watch ret_hu_conv = new Watch(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	public EventsProvider as_EventsProvider() {
		number ret = bindings.ChainMonitor_as_EventsProvider(this.ptr);
		EventsProvider ret_hu_conv = new EventsProvider(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

}
