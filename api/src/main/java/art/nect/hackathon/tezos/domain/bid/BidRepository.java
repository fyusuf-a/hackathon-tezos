package art.nect.hackathon.tezos.domain.bid;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {

	Optional<Bid> findByStripePaymentIntentId(String paymentIntentId);

}