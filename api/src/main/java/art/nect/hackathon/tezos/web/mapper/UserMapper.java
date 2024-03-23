package art.nect.hackathon.tezos.web.mapper;

import org.springframework.stereotype.Component;

import art.nect.hackathon.tezos.domain.user.User;
import art.nect.hackathon.tezos.web.dto.UserDto;

@Component
public class UserMapper {

	public UserDto toDto(User user) {
		return new UserDto()
			.setAddress(user.getAddress())
			.setCreatedAt(user.getCreatedAt());
	}

}