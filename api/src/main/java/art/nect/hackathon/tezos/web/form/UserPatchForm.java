package art.nect.hackathon.tezos.web.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserPatchForm {

	@Email
	@Length(max = 200)
	private String email;

	@Length(max = 200)
	private String name;

}