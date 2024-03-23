package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping
	public String hello(
		@AuthenticationPrincipal DidToken didToken
	) {
		if (didToken == null) {
			return "not connected";
		}

		return didToken.address();
	}

}