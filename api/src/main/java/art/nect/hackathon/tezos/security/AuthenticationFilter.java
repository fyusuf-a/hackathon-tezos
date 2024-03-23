package art.nect.hackathon.tezos.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import art.nect.hackathon.tezos.security.did.DidTokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	public static final String PREFIX = "Bearer ";

	private final DidTokenParser didTokenParser;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.isNotBlank(authorization)) {
			authorize(authorization);
		}

		filterChain.doFilter(request, response);
	}

	private void authorize(String authorization) {
		if (!authorization.startsWith(PREFIX)) {
			return;
		}

		final var token = authorization.substring(PREFIX.length());
		final var didToken = didTokenParser.parse(token);

		final var authentication = new DidTokenAuthentication(didToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	protected boolean shouldNotFilterAsyncDispatch() {
		return true;
	}

	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return true;
	}

}