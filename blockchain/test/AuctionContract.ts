import {
  time,
  loadFixture,
} from "@nomicfoundation/hardhat-toolbox/network-helpers";
import { anyValue } from "@nomicfoundation/hardhat-chai-matchers/withArgs";
import { expect } from "chai";
import hardhat from "hardhat";

const ONE_YEAR_IN_SECS = 365 * 24 * 60 * 60;

async function getDeadlineInOneYear() {
  return (await time.latest()) + ONE_YEAR_IN_SECS;
}

describe("AuctionContract", function () {
  async function deployFixture() {
    const [owner, otherAccount] = await hardhat.ethers.getSigners();

    const NFTContract = await hardhat.ethers.getContractFactory("NectartToken");
    const nftContract = await NFTContract.deploy();

    const ownerNftId = 0;
    const ownerNftId2 = 1;
    const otherAccountNftId = 2;

    await nftContract.safeMint(owner);
    await nftContract.safeMint(owner);
    await nftContract.safeMint(otherAccount);

    expect(await nftContract.ownerOf(ownerNftId)).to.equal(owner);
    expect(await nftContract.ownerOf(ownerNftId2)).to.equal(owner);
    expect(await nftContract.ownerOf(otherAccountNftId)).to.equal(otherAccount);

    const CoinContract = await hardhat.ethers.getContractFactory("USDCToken");
    const coinContract = await CoinContract.deploy();

    const ownerInitialCoin = hardhat.ethers.parseUnits("10", "ether");
    await coinContract.mint(owner, ownerInitialCoin);

    const otherAccountInitialCoin = hardhat.ethers.parseUnits("10", "ether");
    await coinContract.mint(otherAccount, otherAccountInitialCoin);

    const AuctionContract = await hardhat.ethers.getContractFactory(
      "AuctionContract"
    );
    const auctionContract = await AuctionContract.deploy(nftContract);

    await nftContract.approve(auctionContract, ownerNftId);
    await nftContract.approve(auctionContract, ownerNftId2);
    await coinContract.approve(
      auctionContract,
      hardhat.ethers.parseUnits("5", "ether")
    );

    return {
      nftContract,
      coinContract,
      auctionContract,
      owner,
      ownerNftId,
      ownerNftId2,
      ownerInitialCoin,
      otherAccount,
      otherAccountNftId,
      otherAccountInitialCoin,
    };
  }

  async function auctionFixture() {
    const state = await deployFixture();

    const { coinContract, auctionContract, owner, ownerNftId } = state;

    const oneYear = await getDeadlineInOneYear();

    const auctionId = 0;
    await auctionContract.create(ownerNftId, coinContract, oneYear);

    return {
      ...state,
      auctionId,
      oneYear,
    };
  }

  async function claimFixture() {
    const state = await auctionFixture();

    const { coinContract, auctionContract, ownerNftId, otherAccount } = state;

    const five = hardhat.ethers.parseUnits("5", "ether");
    await coinContract.connect(otherAccount).approve(auctionContract, five);

    await auctionContract.connect(otherAccount).bid(ownerNftId, five);

    await time.increase(ONE_YEAR_IN_SECS * 2);

    return {
      ...state,
      five,
    };
  }

  describe("Deployment", function () {
    it("Should set the nft contract", async function () {
      const { auctionContract, nftContract } = await loadFixture(deployFixture);

      expect(await auctionContract.nftContract()).to.equal(nftContract);
    });

    it("Should have no auctions", async function () {
      const { auctionContract } = await loadFixture(deployFixture);

      expect(auctionContract.auctions(0)).to.be.reverted;
    });

    it("Should set the right owner", async function () {
      const { auctionContract, owner } = await loadFixture(deployFixture);

      expect(await auctionContract.owner()).to.equal(owner);
    });
  });

  describe("Auctions", function () {
    describe("Creation", function () {
      it("Should revert if not already approved for transfer", async function () {
        const {
          coinContract,
          auctionContract,
          otherAccount,
          otherAccountNftId,
        } = await loadFixture(deployFixture);

        await expect(
          auctionContract
            .connect(otherAccount)
            .create(otherAccountNftId, coinContract, 0)
        ).to.be.rejectedWith("ERC721InsufficientApproval");
      });

      it("Shouldn't fail if already approved for transfer", async function () {
        const {
          coinContract,
          auctionContract,
          owner,
          ownerNftId,
          ownerNftId2,
        } = await loadFixture(deployFixture);

        await expect(auctionContract.create(ownerNftId2, coinContract, 123))
          .to.emit(auctionContract, "AuctionCreated")
          .withArgs(0);

        expect([...(await auctionContract.auctions(0))]).to.have.members([
          /*seller:*/ owner.address,
          /*tokenId:*/ hardhat.ethers.toBigInt(ownerNftId2),
          /*deadline:*/ hardhat.ethers.toBigInt(123),
          /*coinContract:*/ await coinContract.getAddress(),
          /*bestBidder:*/ owner.address,
          /*bestBid:*/ hardhat.ethers.toBigInt(0),
          /*bestBidIsVirtual:*/ false,
          /*claimedNft:*/ false,
          /*claimedCoin:*/ false,
        ]);

        await expect(auctionContract.create(ownerNftId, coinContract, 456))
          .to.emit(auctionContract, "AuctionCreated")
          .withArgs(1);
      });
    });

    describe("Bid", function () {
      it("Should revert if not already approved for transfer", async function () {
        const { auctionContract, otherAccount, auctionId } = await loadFixture(
          auctionFixture
        );

        await expect(
          auctionContract
            .connect(otherAccount)
            .bid(auctionId, hardhat.ethers.parseUnits("1", "ether"))
        ).to.be.rejectedWith("ERC20InsufficientAllowance");
      });

      it("Shouldn't fail if already approved for transfer", async function () {
        const {
          coinContract,
          auctionContract,
          owner,
          ownerNftId,
          auctionId,
          oneYear,
        } = await loadFixture(auctionFixture);

        const value = hardhat.ethers.parseUnits("1", "ether");

        await expect(auctionContract.bid(auctionId, value))
          .to.emit(auctionContract, "BidPlaced")
          .withArgs(auctionId, owner, value, false);

        expect([...(await auctionContract.auctions(0))]).to.have.members([
          /*seller:*/ owner.address,
          /*tokenId:*/ hardhat.ethers.toBigInt(ownerNftId),
          /*deadline:*/ hardhat.ethers.toBigInt(oneYear),
          /*coinContract:*/ await coinContract.getAddress(),
          /*bestBidder:*/ owner.address,
          /*bestBid:*/ value,
          /*bestBidIsVirtual:*/ false,
          /*claimedNft:*/ false,
          /*claimedCoin:*/ false,
        ]);
      });

      it("Should revert if bid not higher", async function () {
        const { auctionContract, auctionId } = await loadFixture(
          auctionFixture
        );

        await auctionContract.bid(
          auctionId,
          hardhat.ethers.parseUnits("2", "ether")
        );

        await expect(
          auctionContract.bid(
            auctionId,
            hardhat.ethers.parseUnits("1", "ether")
          )
        ).to.be.rejectedWith("Auction: Bid must be higher");
      });

      it("Should revert if after deadline", async function () {
        const { auctionContract, auctionId } = await loadFixture(
          auctionFixture
        );

        await auctionContract.bid(
          auctionId,
          hardhat.ethers.parseUnits("2", "ether")
        );

        await time.increase(ONE_YEAR_IN_SECS * 2);

        await expect(
          auctionContract.bid(
            auctionId,
            hardhat.ethers.parseUnits("3", "ether")
          )
        ).to.be.rejectedWith("Auction: Cannot place bid after deadline");
      });

      it("Should reimburse if outbid", async function () {
        const { coinContract, auctionContract, otherAccount, auctionId } =
          await loadFixture(auctionFixture);

        const one = hardhat.ethers.parseUnits("1", "ether");
        await coinContract.connect(otherAccount).approve(auctionContract, one);

        await auctionContract.connect(otherAccount).bid(auctionId, one);

        await expect(
          auctionContract.bid(
            auctionId,
            hardhat.ethers.parseUnits("3", "ether")
          )
        )
          .to.emit(coinContract, "Transfer")
          .withArgs(auctionContract, otherAccount, one);
      });
    });

    describe("Claim", function () {
      describe("NFT", function () {
        it("Should revert if before deadline", async function () {
          const { auctionContract, auctionId } = await loadFixture(
            auctionFixture
          );

          await expect(auctionContract.claimNft(auctionId)).be.revertedWith(
            "Auction: Deadline not yet reached"
          );
        });

        it("Shouldn't revert if after deadline", async function () {
          const {
            nftContract,
            coinContract,
            auctionContract,
            owner,
            ownerNftId,
            otherAccount,
            oneYear,
            auctionId,
            five,
          } = await loadFixture(claimFixture);

          await expect(auctionContract.claimNft(auctionId))
            .to.emit(nftContract, "Transfer")
            .withArgs(auctionContract, otherAccount, ownerNftId);

          expect([...(await auctionContract.auctions(0))]).to.have.members([
            /*seller:*/ owner.address,
            /*tokenId:*/ hardhat.ethers.toBigInt(ownerNftId),
            /*deadline:*/ hardhat.ethers.toBigInt(oneYear),
            /*coinContract:*/ await coinContract.getAddress(),
            /*bestBidder:*/ otherAccount.address,
            /*bestBid:*/ five,
            /*bestBidIsVirtual:*/ false,
            /*claimedNft:*/ true,
            /*claimedCoin:*/ false,
          ]);
        });
        
        it("Should revert if before deadline", async function () {
          const { auctionContract, auctionId } = await loadFixture(
            claimFixture
          );

          auctionContract.claimNft(auctionId);

          await expect(auctionContract.claimNft(auctionId)).to.be.revertedWith(
            "Auction: NFT already claimed"
          );
        });
      });

      describe("Coin", function () {
        it("Should revert if before deadline", async function () {
          const { auctionContract, auctionId } = await loadFixture(
            auctionFixture
          );

          await expect(auctionContract.claimCoin(auctionId)).be.revertedWith(
            "Auction: Deadline not yet reached"
          );
        });

        it("Shouldn't revert if after deadline", async function () {
          const {
            coinContract,
            auctionContract,
            owner,
            ownerNftId,
            otherAccount,
            oneYear,
            auctionId,
            five,
          } = await loadFixture(claimFixture);

          await expect(auctionContract.claimCoin(auctionId))
            .to.emit(coinContract, "Transfer")
            .withArgs(auctionContract, owner, five);

          expect([...(await auctionContract.auctions(0))]).to.have.members([
            /*seller:*/ owner.address,
            /*tokenId:*/ hardhat.ethers.toBigInt(ownerNftId),
            /*deadline:*/ hardhat.ethers.toBigInt(oneYear),
            /*coinContract:*/ await coinContract.getAddress(),
            /*bestBidder:*/ otherAccount.address,
            /*bestBid:*/ five,
            /*bestBidIsVirtual:*/ false,
            /*claimedNft:*/ false,
            /*claimedCoin:*/ true,
          ]);
        });

        it("Should revert if before deadline", async function () {
          const { auctionContract, auctionId } = await loadFixture(
            claimFixture
          );

          auctionContract.claimCoin(auctionId);

          await expect(auctionContract.claimCoin(auctionId)).to.be.revertedWith(
            "Auction: Coin already claimed"
          );
        });
      });
    });
  });
});
