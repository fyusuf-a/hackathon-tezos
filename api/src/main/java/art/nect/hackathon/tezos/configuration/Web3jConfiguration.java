package art.nect.hackathon.tezos.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import art.nect.hackathon.tezos.configuration.properties.Web3Properties;
import art.nect.hackathon.tezos.contract.AuctionContract;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class Web3jConfiguration {

	@Bean
	Web3j web3j(Web3Properties properties) throws IOException {
		log.info("Configuring Web3");

		final var builder = new OkHttpClient.Builder();

		if (properties.isDebug()) {
			final var logging = new HttpLoggingInterceptor(log::debug);
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);

			builder.addInterceptor(logging);
		}

		final var seconds = properties.getHttpTimeoutSeconds();
		if (seconds != null) {
			builder.connectTimeout(seconds, TimeUnit.SECONDS);
			builder.readTimeout(seconds, TimeUnit.SECONDS);
			builder.writeTimeout(seconds, TimeUnit.SECONDS);
		}

		final var service = new HttpService(
			properties.getClientAddress(),
			builder.build()
		);

		return Web3j.build(service);
	}

	@Bean
	TransactionManager web3TransactionManager(Web3j web3j, Credentials web3Credentials, Web3Properties properties) {
		return new FastRawTransactionManager(web3j, web3Credentials, properties.getChainId());
	}

	@Bean
	ContractGasProvider contractGasProvider() {
		return new DefaultGasProvider();
	}

	@Bean
	Credentials web3Credentials(Web3Properties properties) {
		return Credentials.create(properties.getPrivateKey());
	}

	@Bean
	@SneakyThrows
	String chainId(Web3j web3j) {
		final var chainId = web3j.ethChainId().send().getChainId().toString();

		log.info("chainId={}", chainId);

		return chainId;
	}

	@Bean
	AuctionContract auctionContract(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Web3Properties properties) {
		final var address = properties.getContracts().getAuction();

		return AuctionContract.load(address, web3j, transactionManager, contractGasProvider);
	}

}