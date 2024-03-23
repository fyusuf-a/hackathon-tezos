package art.nect.hackathon.tezos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.UnauthorizedEntryPoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	private final UnauthorizedEntryPoint unauthorizedEntryPoint;
	private final AuthenticationFilter authenticationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.exceptionHandling((handling) -> {
				handling.authenticationEntryPoint(unauthorizedEntryPoint);
			})
			.authorizeHttpRequests((matcher) -> {
				matcher.requestMatchers("/spec.json/**", "/swagger-ui.html", "/swagger-ui/**", "/").permitAll();
				matcher.requestMatchers("/auth/login").permitAll();
				matcher.requestMatchers("/error").permitAll();
				matcher.requestMatchers("/**").fullyAuthenticated();
			})
			.sessionManagement((session) -> {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.getOrBuild();
	}

}