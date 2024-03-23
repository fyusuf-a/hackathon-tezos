package art.nect.hackathon.tezos.security.did;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.Claim;

@Component
public class DidTokenValidator {

	public void validate(String proof, String rawClaims, Map<String, Claim> claims) {
		// TODO Impl
	}

}