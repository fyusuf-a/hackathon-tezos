package art.nect.hackathon.tezos.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import art.nect.hackathon.tezos.security.did.DidToken;

public record DidTokenAuthentication(
	DidToken token
) implements Authentication {

	@Override
	public String getName() {
		return token.issuer();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getDetails() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

}