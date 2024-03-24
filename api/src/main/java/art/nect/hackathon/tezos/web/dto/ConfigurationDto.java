package art.nect.hackathon.tezos.web.dto;

import art.nect.hackathon.tezos.configuration.properties.Web3Properties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "Configuration")
public class ConfigurationDto {

	private Web3Properties.Contracts web3Contracts;
	private String stripePublicKey;

}