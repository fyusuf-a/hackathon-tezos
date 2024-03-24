package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.domain.user.UserService;
import art.nect.hackathon.tezos.security.authz.Authenticated;
import art.nect.hackathon.tezos.security.did.DidToken;
import art.nect.hackathon.tezos.web.dto.UserDto;
import art.nect.hackathon.tezos.web.form.UserPatchForm;
import art.nect.hackathon.tezos.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Authenticated
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/users/@me", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.USER)
public class UserMeRestController {

	private final UserService userService;

	private final UserMapper userMapper;

	@GetMapping
	public UserDto show(
		@AuthenticationPrincipal DidToken didToken
	) {
		final var user = userService.get(didToken.address());

		return userMapper.toDto(user);
	}

	@PatchMapping
	public UserDto patch(
		@RequestBody UserPatchForm form,
		@AuthenticationPrincipal DidToken didToken
	) {
		final var user = userService.get(didToken.address());
		userService.update(user, form);

		return userMapper.toDto(user);
	}

}