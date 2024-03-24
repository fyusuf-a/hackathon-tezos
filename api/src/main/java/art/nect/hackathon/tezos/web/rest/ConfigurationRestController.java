package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.nect.hackathon.tezos.configuration.properties.StripeProperties;
import art.nect.hackathon.tezos.configuration.properties.Web3Properties;
import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.web.dto.ConfigurationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.CONFIGURATION)
public class ConfigurationRestController {

	private final Web3Properties web3Properties;
	private final StripeProperties stripeProperties;

	@GetMapping
	public ConfigurationDto show() {
		return new ConfigurationDto()
			.setWeb3Contracts(web3Properties.getContracts())
			.setStripePublicKey(stripeProperties.getPublicKey());
	}

}