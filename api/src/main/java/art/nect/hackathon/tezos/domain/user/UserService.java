package art.nect.hackathon.tezos.domain.user;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.stripe.StripeClient;
import com.stripe.param.CustomerCreateParams;

import art.nect.hackathon.tezos.domain.user.exception.UserNotFoundException;
import art.nect.hackathon.tezos.web.form.UserPatchForm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final StripeClient stripeClient;

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

	@SneakyThrows
	public synchronized User validateStripeCustomer(User user) {
		user = repository.findById(user.getId()).get();

		if (user.getStripeCustomerId() == null) {
			final var customer = stripeClient.customers().create(
				new CustomerCreateParams.Builder()
					.setEmail(user.getEmail())
					.setName(user.getName())
					.setMetadata(Map.of(
						"address", user.getAddress()
					))
					.build()
			);

			repository.save(
				user
					.setStripeCustomerId(customer.getId())
			);
		}

		return user;
	}

}