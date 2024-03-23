package art.nect.hackathon.tezos.web.rest;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;

import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.security.authz.Authenticated;
import art.nect.hackathon.tezos.security.did.DidToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Authenticated
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.HELLO)
public class HelloRestControllerV1 {

	private final Web3j web3j;

	@GetMapping
	public String hello(
		@AuthenticationPrincipal DidToken didToken
	) {
		if (didToken == null) {
			return "not connected";
		}

		return didToken.address();
	}

	@GetMapping("balance")
	@Authenticated
	public String balance(
		@AuthenticationPrincipal DidToken didToken
	) throws IOException {
		return web3j.ethGetBalance(didToken.address(), DefaultBlockParameterName.LATEST)
			.send()
			.getBalance()
			.toString();
	}

}