import { buildModule } from "@nomicfoundation/hardhat-ignition/modules";

const NectartTokenModule = buildModule("NectartTokenModule", (m) => {
  const nectartToken = m.contract("NectartToken");

  return { nectartToken };
});

export default NectartTokenModule;
