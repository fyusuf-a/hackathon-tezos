package art.nect.hackathon.tezos.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;
import com.stripe.StripeClient;

import art.nect.hackathon.tezos.configuration.properties.StripeProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class StripeConfiguration {

	@Bean
	StripeClient stripeClient(StripeProperties properties) throws IOException {
		log.info("Configuring Stripe");

		Stripe.enableTelemetry = false;

		return new StripeClient(properties.getSecretKey());
	}

}