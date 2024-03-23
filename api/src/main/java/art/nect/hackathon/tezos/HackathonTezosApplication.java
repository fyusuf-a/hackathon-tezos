package art.nect.hackathon.tezos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HackathonTezosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackathonTezosApplication.class, args);
	}

}