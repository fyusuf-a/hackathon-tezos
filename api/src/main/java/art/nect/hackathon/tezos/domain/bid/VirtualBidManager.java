package art.nect.hackathon.tezos.domain.bid;

import java.math.BigInteger;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import art.nect.hackathon.tezos.contract.AuctionContract;
import art.nect.hackathon.tezos.contract.USDCTokenFactory;
import art.nect.hackathon.tezos.domain.bid.event.BidPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VirtualBidManager {

	private final AuctionContract auctionContract;
	private final USDCTokenFactory usdcTokenFactory;
	private final BidRepository bidRepository;

	@EventListener
	public void onBidPaid(BidPaidEvent event) {
		final var bid = event.getBid();

		try {
			final var auctionId = BigInteger.valueOf(bid.getAuctionId());
			final var bidder = bid.getUser().getAddress();

			final var coinTokenAddress = auctionContract.auctions(auctionId).send().component4();
			final var usdcToken = usdcTokenFactory.load(coinTokenAddress);

			final var decimals = usdcToken.decimals().send();
			final var one = BigInteger.TEN.pow(decimals.intValue());
			final var amount = one.multiply(BigInteger.valueOf(bid.getAmount()));

			log.info("will send transaction - auctionId={} bidder={}, amount={}", auctionId, bidder, amount);

			final var receipt = auctionContract.bidVirtually(
				auctionId,
				bidder,
				amount
			).send();

			bidRepository.save(
				bid
					.setTransactionHash(receipt.getTransactionHash())
			);
		} catch (Exception exception) {
			log.error("could not transmit virtual bid - bidId=%s".formatted(bid.getId()), exception);
		}
	}

}