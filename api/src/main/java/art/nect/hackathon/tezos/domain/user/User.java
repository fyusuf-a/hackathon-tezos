package art.nect.hackathon.tezos.domain.user;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "users", uniqueConstraints = {
	@UniqueConstraint(name = User.UNIQUE_ADDRESS_CONTRAINT, columnNames = {
		User.Fields.address
	})
})
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

	public static final String UNIQUE_ADDRESS_CONTRAINT = "user__unique__address";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@Column(nullable = false)
	private String address;

	@Column(nullable = true)
	private String email;

	@Column(nullable = true)
	private String name;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

}