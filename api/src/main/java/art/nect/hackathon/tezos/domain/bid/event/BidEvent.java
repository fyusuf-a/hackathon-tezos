package art.nect.hackathon.tezos.domain.bid.event;

import org.springframework.context.ApplicationEvent;

import art.nect.hackathon.tezos.domain.bid.Bid;
import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class BidEvent extends ApplicationEvent {
	
	private final Bid bid;

	public BidEvent(Bid bid, Object source) {
		super(source);
		
		this.bid = bid;
	}

}