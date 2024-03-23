package art.nect.hackathon.tezos.web.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {
	
	@NotBlank
	private String didToken;

}