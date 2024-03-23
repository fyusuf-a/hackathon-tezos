package art.nect.hackathon.tezos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.UnauthorizedEntryPoint;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorCodeMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorMessageMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.HttpStatusMapper;
import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	UnauthorizedEntryPoint unauthorizedEntryPoint(HttpStatusMapper httpStatusMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper, ObjectMapper objectMapper) {
		return new UnauthorizedEntryPoint(httpStatusMapper, errorCodeMapper, errorMessageMapper, objectMapper);
	}

}