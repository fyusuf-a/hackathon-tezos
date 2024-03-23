package art.nect.hackathon.tezos.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import art.nect.hackathon.tezos.configuration.properties.Web3Properties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class Web3jConfiguration {

	@Bean
	Web3j web3j(Web3Properties properties) throws IOException {
		log.info("Configuring Web3");

		final var service = new HttpService(
			properties.getClientAddress(),
			createOkHttpClient(properties)
		);

		return Web3j.build(service);
	}

	public static OkHttpClient createOkHttpClient(Web3Properties properties) {
		final var builder = new OkHttpClient.Builder();
		configureLogging(properties, builder);
		configureTimeouts(properties, builder);
		return builder.build();
	}

	public static void configureTimeouts(Web3Properties properties, OkHttpClient.Builder builder) {
		final var seconds = properties.getHttpTimeoutSeconds();

		if (seconds != null) {
			builder.connectTimeout(seconds, TimeUnit.SECONDS);
			builder.readTimeout(seconds, TimeUnit.SECONDS);
			builder.writeTimeout(seconds, TimeUnit.SECONDS);
		}
	}

	public static void configureLogging(Web3Properties properties, OkHttpClient.Builder builder) {
		if (properties.isDebug()) {
			final var logging = new HttpLoggingInterceptor(log::debug);
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);

			builder.addInterceptor(logging);
		}
	}

}