// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.24;

// Uncomment this line to use console.log
// import "hardhat/console.sol";

import "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721Receiver.sol";
import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract AuctionContract is IERC721Receiver, Ownable, ReentrancyGuard {
    struct Auction {
        address seller;
        uint256 tokenId;
        uint256 deadline;
        IERC20 coinContract;
        /* */
        address bestBidder;
        uint256 bestBid;
        bool bestBidIsVirtual;
        /* */
        bool claimedNft;
        bool claimedCoin;
    }

    event AuctionCreated(uint auctionId);

    event BidPlaced(
        uint auctionId,
        address bidder,
        uint256 value,
        bool isVirtual
    );

    event NFTClaimed(uint auctionId);

    event CoinClaimed(uint auctionId, bool isVirtual);

    IERC721 public nftContract;
    Auction[] public auctions;

    constructor(IERC721 nftContract_) Ownable(msg.sender) {
        nftContract = nftContract_;
    }

    function create(
        uint256 tokenId,
        IERC20 coinContract,
        uint256 deadline
    ) external {
        nftContract.safeTransferFrom(msg.sender, address(this), tokenId);

        Auction memory auction = Auction({
            seller: msg.sender,
            tokenId: tokenId,
            deadline: deadline,
            coinContract: coinContract,
            /* */
            bestBidder: msg.sender,
            bestBid: 0,
            bestBidIsVirtual: false,
            /* */
            claimedNft: false,
            claimedCoin: false
        });

        uint256 auctionId = auctions.length;
        auctions.push(auction);
        emit AuctionCreated(auctionId);
    }

    function bid(uint256 auctionId, uint256 value) external nonReentrant {
        Auction storage auction = auctions[auctionId];
        auction.coinContract.transferFrom(msg.sender, address(this), value);

        _bid(auctionId, msg.sender, value, false);
    }

    function bidVirtually(
        uint256 auctionId,
        address bidder,
        uint256 value
    ) external onlyOwner {
        _bid(auctionId, bidder, value, true);
    }

    function _bid(
        uint256 auctionId,
        address bidder,
        uint256 value,
        bool isVirtual
    ) private {
        Auction storage auction = auctions[auctionId];

        require(value > auction.bestBid, "Auction: Bid must be higher");
        require(block.timestamp < auction.deadline, "Auction: Cannot place bid after deadline");

        if (!auction.bestBidIsVirtual) {
            auction.coinContract.transfer(auction.bestBidder, auction.bestBid);
        }

        auction.bestBidder = bidder;
        auction.bestBid = value;
        auction.bestBidIsVirtual = isVirtual;

        emit BidPlaced(auctionId, bidder, value, isVirtual);
    }

    function claimNft(uint256 auctionId) external {
        Auction storage auction = auctions[auctionId];

        requireEnded(auction);
        require(!auction.claimedNft, "Auction: NFT already claimed");

        auction.claimedNft = true;

        nftContract.safeTransferFrom(
            address(this),
            auction.bestBidder,
            auction.tokenId
        );

        emit NFTClaimed(auctionId);
    }

    function claimCoin(uint256 auctionId) external {
        Auction storage auction = auctions[auctionId];

        requireEnded(auction);
        require(!auction.claimedCoin, "Auction: Coin already claimed");

        auction.claimedCoin = true;

        if (!auction.bestBidIsVirtual) {
            auction.coinContract.transfer(auction.seller, auction.bestBid);
        }

        emit CoinClaimed(auctionId, auction.bestBidIsVirtual);
    }

    function requireEnded(Auction storage auction) internal view {
        require(
            block.timestamp >= auction.deadline,
            "Auction: Deadline not yet reached"
        );
    }

    function onERC721Received(
        address /*operator*/,
        address /*from*/,
        uint256 /*tokenId*/,
        bytes calldata /*data*/
    ) external pure returns (bytes4) {
        return IERC721Receiver.onERC721Received.selector;
    }
}
