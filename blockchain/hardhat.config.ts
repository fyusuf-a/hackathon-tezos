import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";

if (!process.env.PRIVATE_KEY) {
  throw new Error("PRIVATE_KEY is not set");
}

const config: HardhatUserConfig = {
  solidity: "0.8.24",
  networks: {
    'etherlink-testnet': {
      url: 'https://node.ghostnet.etherlink.com',
      chainId: 128123,
      accounts: [process.env.PRIVATE_KEY],
    },
  },
};

export default config;
