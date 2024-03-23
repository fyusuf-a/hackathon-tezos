import { buildModule } from "@nomicfoundation/hardhat-ignition/modules";

const AuctionModule = buildModule("AuctionModule", (m) => {
  const NectartToken = m.contract("NectartToken");
  const AuctionContract = m.contract("AuctionContract", [NectartToken]);

  return { NectartToken, AuctionContract };
});

export default AuctionModule;
