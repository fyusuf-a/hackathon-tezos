package art.nect.hackathon.tezos.domain.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;

	public User create(String address) {
		try {
			return repository.save(
				new User()
					.setAddress(address)
			);
		} catch (DataIntegrityViolationException exception) {
			final var message = String.valueOf(exception);

			if (message.contains(User.UNIQUE_ADDRESS_CONTRAINT)) {
				return repository.findByAddress(address).get();
			}

			throw exception;
		}
	}

}