package art.nect.hackathon.tezos.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author https://github.com/web3j/web3j-spring-boot-starter/blob/master/src/main/java/org/web3j/spring/autoconfigure/Web3jProperties.java
 */
@Data
@Validated
@Component
@ConfigurationProperties(prefix = Web3Properties.PREFIX)
public class Web3Properties {

	public static final String PREFIX = "web3";

	@NotBlank
	private String clientAddress;
	private Long httpTimeoutSeconds;
	private boolean debug;

	@NotBlank
	private String privateKey;

	@Positive
	public long chainId;

	@NotNull
	@Valid
	public Contracts contracts;

	@Data
	public static class Contracts {

		@NotBlank
		private String usdc;

		@NotBlank
		private String nectart;

		@NotBlank
		private String auction;

	}

}