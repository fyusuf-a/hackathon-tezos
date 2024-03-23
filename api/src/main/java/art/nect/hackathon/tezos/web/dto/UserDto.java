package art.nect.hackathon.tezos.web.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
	
	private String address;
	private LocalDateTime createdAt;

}