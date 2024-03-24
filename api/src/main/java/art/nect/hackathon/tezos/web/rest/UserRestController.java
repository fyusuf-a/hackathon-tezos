package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import art.nect.hackathon.tezos.configuration.properties.AvatarProperties;
import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.domain.user.UserService;
import art.nect.hackathon.tezos.web.dto.UserDto;
import art.nect.hackathon.tezos.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/users/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.USER)
public class UserRestController {

	private final AvatarProperties avatarProperties;
	private final UserService userService;

	private final UserMapper userMapper;

	@GetMapping
	public UserDto show(
		@PathVariable String address
	) {
		final var user = userService.get(address);

		return userMapper.toDto(user);
	}

	@GetMapping("/avatar")
	public RedirectView avatar(
		@PathVariable String address
	) {
		final var view = new RedirectView();
		view.setUrl(avatarProperties.formatUrl(address));
		view.setStatusCode(HttpStatus.PERMANENT_REDIRECT);

		return view;
	}

}