package art.nect.hackathon.tezos.domain.bid;

import java.math.BigInteger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.param.PaymentIntentCreateParams;

import art.nect.hackathon.tezos.configuration.properties.StripeProperties;
import art.nect.hackathon.tezos.contract.AuctionContract;
import art.nect.hackathon.tezos.domain.bid.event.BidPaidEvent;
import art.nect.hackathon.tezos.domain.bid.exception.BidNotFoundException;
import art.nect.hackathon.tezos.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {

	private final BidRepository repository;
	private final AuctionContract auctionContract;
	private final StripeClient stripeClient;
	private final StripeProperties stripeProperties;
	private final ApplicationEventPublisher eventPublisher;

	public Bid get(long id) {
		return repository.findById(id)
			.orElseThrow(() -> new BidNotFoundException(id));
	}

	@SneakyThrows
	public Bid create(User user, long auctionId, long amount) {
		auctionContract.auctions(BigInteger.valueOf(auctionId)).send();

		final var paymentIntent = stripeClient.paymentIntents().create(
			new PaymentIntentCreateParams.Builder()
				.setCurrency(stripeProperties.getCurrency())
				.setAmount(amount * 100)
				.build()
		);

		return repository.save(
			new Bid()
				.setUser(user)
				.setAuctionId(auctionId)
				.setAmount(amount)
				.setStripePaymentIntentId(paymentIntent.getId())
				.setStripeClientSecret(paymentIntent.getClientSecret())
		);
	}

	public synchronized void markSuccessful(String paymentIntentId) {
		final var bid = repository.findByStripePaymentIntentId(paymentIntentId).orElse(null);
		if (bid == null) {
			log.warn("no bid found - paymentIntentId={}", paymentIntentId);
			return;
		}

		if (bid.isSuccess()) {
			log.warn("bid already marked as success - bidId={}", bid.getId());
			return;
		}

		repository.saveAndFlush(
			bid
				.setSuccess(true)
		);

		eventPublisher.publishEvent(new BidPaidEvent(bid, paymentIntentId));
	}

}