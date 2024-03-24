package art.nect.hackathon.tezos.domain.bid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorProperty;
import lombok.Getter;

@Getter
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BidNotFoundException extends RuntimeException {

	@ResponseErrorProperty
	private final long bidId;

	public BidNotFoundException(long bidId) {
		super("bid not found");

		this.bidId = bidId;
	}

}