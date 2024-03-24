package art.nect.hackathon.tezos.domain.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import art.nect.hackathon.tezos.domain.user.exception.UserNotFoundException;
import art.nect.hackathon.tezos.web.form.UserPatchForm;
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
				return get(address);
			}

			throw exception;
		}
	}

	public User get(String address) {
		return repository.findByAddress(address)
			.orElseThrow(() -> new UserNotFoundException(address));
	}

	public User update(User user, UserPatchForm form) {
		if (form.getEmail() != null) {
			user.setEmail(form.getEmail());
		}

		if (form.getName() != null) {
			user.setName(form.getName());
		}

		return repository.save(user);
	}

}