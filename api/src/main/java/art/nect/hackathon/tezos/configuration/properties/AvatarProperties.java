package art.nect.hackathon.tezos.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = AvatarProperties.PREFIX)
public class AvatarProperties {

	public static final String PREFIX = "app.avatar";

	@NotBlank
	private String urlFormat;

	public String formatUrl(String address) {
		return urlFormat.replace("{address}", address);
	}

}