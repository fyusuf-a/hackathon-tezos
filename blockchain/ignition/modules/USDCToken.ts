import { buildModule } from "@nomicfoundation/hardhat-ignition/modules";

const USDCTokenModule = buildModule("USDCTokenModule", (m) => {
  const usdcToken = m.contract("USDCToken");

  return { usdcToken };
});

export default USDCTokenModule;
