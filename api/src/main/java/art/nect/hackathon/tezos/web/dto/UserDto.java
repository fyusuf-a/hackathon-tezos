package art.nect.hackathon.tezos.web.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "User")
public class UserDto {

	private String address;
	private String email;
	private String name;
	private LocalDateTime createdAt;

}