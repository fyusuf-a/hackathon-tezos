package art.nect.hackathon.tezos.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BidCreateForm {

	@NotNull
	@PositiveOrZero
	private Long auctionId;

	@NotNull
	@Positive
	private Long amount;

}