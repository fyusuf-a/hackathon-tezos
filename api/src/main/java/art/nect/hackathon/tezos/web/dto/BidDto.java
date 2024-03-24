package art.nect.hackathon.tezos.web.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "Bid")
public class BidDto {

	private long id;
	private long userId;
	private long auctionId;
	private long amount;
	private String stripePaymentIntentId;
	private String stripeClientSecret;
	private boolean success;
	private String transactionHash;
	private LocalDateTime createdAt;

}