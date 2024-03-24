package art.nect.hackathon.tezos.domain.bid;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import art.nect.hackathon.tezos.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "bids")
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@ManyToOne(optional = false)
	private User user;

	@Column(nullable = false)
	private long auctionId;

	@Column(nullable = false)
	private long amount;

	@Column(nullable = false)
	private String stripePaymentIntentId;

	@Column(nullable = false)
	private String stripeClientSecret;

	@Column(nullable = false)
	private boolean success;

	@Column(nullable = true)
	private String transactionHash;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

}