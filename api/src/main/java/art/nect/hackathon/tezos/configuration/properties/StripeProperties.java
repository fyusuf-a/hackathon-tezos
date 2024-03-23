package art.nect.hackathon.tezos.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = StripeProperties.PREFIX)
public class StripeProperties {

	public static final String PREFIX = "stripe";

	@NotBlank
	@ToString.Exclude
	private String publicKey;

	@NotBlank
	@ToString.Exclude
	private String secretKey;

	@NotBlank
	@ToString.Exclude
	private String endpointSecret;

}