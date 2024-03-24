package art.nect.hackathon.tezos.web.rest.external;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.net.Webhook;

import art.nect.hackathon.tezos.configuration.properties.StripeProperties;
import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.domain.bid.BidService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/external/stripe", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.EXTERNAL)
public class ExternalStripeRestControllerV1 {

	private final StripeProperties stripeProperties;
	
	private final BidService bidService;

	@SuppressWarnings("deprecation")
	@PostMapping("/webhook")
	public void webhook(
		@RequestHeader("Stripe-Signature") String signature,
		@RequestBody String payload
	) throws SignatureVerificationException {
		final var event = Webhook.constructEvent(payload, signature, stripeProperties.getEndpointSecret());
		final var object = event.getData().getObject();

		switch (event.getType()) {

			case "customer.created": {
				final var customer = ((Customer) object);
				log.info("created customer - id={} email={}", customer.getId(), customer.getEmail());

				break;
			}

			case "charge.succeeded": {
				final var charge = ((Charge) object);
				final var paymentIntentId = charge.getPaymentIntent();
				log.info("succeeded charge - id={} paymentIntentId={}", charge.getId(), paymentIntentId);

				bidService.markSuccessful(paymentIntentId);
				
				break;
			}

			default: {
				log.warn("unhandled webhook event - type={}", event.getType());
				break;
			}

		}
	}

}