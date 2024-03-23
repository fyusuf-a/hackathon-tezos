package art.nect.hackathon.tezos.security.did;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DidTokenParser {

	public static final TypeReference<List<String>> LIST_OF_STRINGS = new TypeReference<>() {};
	public static final TypeReference<Map<String, JsonNode>> MAP_STRING_TO_JSON_NODE = new TypeReference<>() {};

	private final ObjectMapper objectMapper;

	public DidToken parse(String token) {
		final String decoded;
		try {
			decoded = new String(Base64.decodeBase64(token), StandardCharsets.UTF_8);
		} catch (Exception exception) {
			throw new MalformedTokenException("Could not decode base64", exception);
		}

		final List<String> parts;
		try {
			parts = objectMapper.readValue(decoded, LIST_OF_STRINGS);
		} catch (JacksonException exception) {
			throw new MalformedTokenException("Could not parse parts", exception);
		}

		if (parts == null || parts.size() != 2) {
			throw new MalformedTokenException("parts size must be 2");
		}

		final var proof = parts.get(0);
		final var rawClaims = parts.get(1);

		final var claims = parseClaims(rawClaims);
		validate(proof, claims, rawClaims);
		
		final var address = claims.get("iss").asString().substring("did:ethr:".length());

		return new DidToken(proof, rawClaims, claims, address);
	}

	public void validate(String proof, Map<String, Claim> claims, String rawClaims) {
		// TODO Impl
	}

	private Map<String, Claim> parseClaims(String raw) {
		final Map<String, JsonNode> root;
		try {
			root = objectMapper.readValue(raw, MAP_STRING_TO_JSON_NODE);
		} catch (JacksonException exception) {
			throw new MalformedTokenException("Could not parse claims", exception);
		}

		return root.entrySet()
			.stream()
			.map((entry) -> Map.entry(entry.getKey(), JsonNodeClaim.claimFromNode(entry.getValue(), objectMapper)))
			.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
	}

}