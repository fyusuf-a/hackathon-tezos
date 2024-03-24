package art.nect.hackathon.tezos.domain.bid;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.param.PaymentIntentCreateParams;

import art.nect.hackathon.tezos.configuration.properties.StripeProperties;
import art.nect.hackathon.tezos.contract.AuctionContract;
import art.nect.hackathon.tezos.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class BidService {

	private final BidRepository bidRepository;
	private final AuctionContract auctionContract;
	private final StripeClient stripeClient;
	private final StripeProperties stripeProperties;

	@SneakyThrows
	public Bid create(User user, long auctionId, long amount) {
		auctionContract.auctions(BigInteger.valueOf(auctionId)).send();

		final var paymentIntent = stripeClient.paymentIntents().create(
			new PaymentIntentCreateParams.Builder()
				.setCurrency(stripeProperties.getCurrency())
				.setAmount(amount * 100)
				.build()
		);

		return bidRepository.save(
			new Bid()
				.setUser(user)
				.setAuctionId(auctionId)
				.setAmount(amount)
				.setStripePaymentIntentId(paymentIntent.getId())
		);
	}

}