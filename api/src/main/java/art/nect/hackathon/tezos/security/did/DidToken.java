package art.nect.hackathon.tezos.security.did;

import java.util.Map;

import com.auth0.jwt.interfaces.Claim;

public record DidToken(
	String proof,
	String raw,
	Map<String, Claim> claims,
	String address
) {

	public String issuer() {
		return claims.get("iss").asString();
	}

}