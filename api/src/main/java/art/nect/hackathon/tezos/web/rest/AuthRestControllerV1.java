package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.domain.user.UserService;
import art.nect.hackathon.tezos.security.authz.Authenticated;
import art.nect.hackathon.tezos.security.did.DidTokenParser;
import art.nect.hackathon.tezos.web.dto.UserDto;
import art.nect.hackathon.tezos.web.form.LoginForm;
import art.nect.hackathon.tezos.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Authenticated
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.AUTH)
public class AuthRestControllerV1 {

	private final DidTokenParser didTokenParser;
	private final UserService userService;
	
	private final UserMapper userMapper;

	@PostMapping("/login")
	public UserDto login(
		@RequestBody @Validated LoginForm form
	) {
		final var didToken = didTokenParser.parse(form.getDidToken());
		final var user = userService.create(didToken.address());

		return userMapper.toDto(user);
	}

}