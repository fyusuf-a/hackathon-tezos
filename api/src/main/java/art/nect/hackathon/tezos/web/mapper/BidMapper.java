package art.nect.hackathon.tezos.web.mapper;

import org.springframework.stereotype.Component;

import art.nect.hackathon.tezos.domain.bid.Bid;
import art.nect.hackathon.tezos.web.dto.BidDto;

@Component
public class BidMapper {

	public BidDto toDto(Bid bid) {
		return new BidDto()
			.setId(bid.getId())
			.setUserId(bid.getUser().getId())
			.setAuctionId(bid.getAuctionId())
			.setAmount(bid.getAmount())
			.setStripePaymentIntentId(bid.getStripePaymentIntentId())
			.setCreatedAt(bid.getCreatedAt());
	}

}