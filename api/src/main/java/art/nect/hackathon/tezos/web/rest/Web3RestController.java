package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.nect.hackathon.tezos.configuration.properties.Web3Properties;
import art.nect.hackathon.tezos.constant.Tags;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/web3", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.WEB3)
public class Web3RestController {

	private final Web3Properties web3Properties;

	@GetMapping("/contracts")
	public Web3Properties.Contracts contracts() {
		return web3Properties.getContracts();
	}

}