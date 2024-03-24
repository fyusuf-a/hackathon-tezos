package art.nect.hackathon.tezos.web.rest;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.nect.hackathon.tezos.constant.Tags;
import art.nect.hackathon.tezos.domain.bid.BidService;
import art.nect.hackathon.tezos.domain.user.UserService;
import art.nect.hackathon.tezos.security.authz.Authenticated;
import art.nect.hackathon.tezos.security.did.DidToken;
import art.nect.hackathon.tezos.web.dto.BidDto;
import art.nect.hackathon.tezos.web.form.BidCreateForm;
import art.nect.hackathon.tezos.web.mapper.BidMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Authenticated
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/bids", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = Tags.BID)
public class BidRestController {

	private final UserService userService;
	private final BidService bidService;

	private final BidMapper bidMapper;

	@PostMapping
	public BidDto create(
		@RequestBody BidCreateForm form,
		@AuthenticationPrincipal DidToken didToken
	) {
		var user = userService.get(didToken.address());
		user = userService.validateStripeCustomer(user);

		final var bid = bidService.create(user, form.getAuctionId(), form.getAmount());

		return bidMapper.toDto(bid);
	}

	@GetMapping("/{bidId}")
	public BidDto show(
		@PathVariable long bidId,
		@AuthenticationPrincipal DidToken didToken
	) {
		final var bid = bidService.get(bidId);

		if (!bid.getUser().getAddress().equals(didToken.address())) {
			throw new AccessDeniedException("not the owner");
		}

		return bidMapper.toDto(bid);
	}

}