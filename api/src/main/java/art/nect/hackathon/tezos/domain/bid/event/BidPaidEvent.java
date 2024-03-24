package art.nect.hackathon.tezos.domain.bid.event;

import art.nect.hackathon.tezos.domain.bid.Bid;

@SuppressWarnings("serial")
public class BidPaidEvent extends BidEvent {

	public BidPaidEvent(Bid bid, Object source) {
		super(bid, source);
	}

}