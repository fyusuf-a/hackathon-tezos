import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import "@nomicfoundation/hardhat-verify";
import dotenv from "dotenv";

dotenv.config()
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
  etherscan: {
    enabled: true,
    apiKey: "x",
    customChains: [
      {
        network: "etherlink-testnet",
        chainId: 128123,
        urls: {
          browserURL: "https://testnet-explorer.etherlink.com",
          apiURL: "https://testnet-explorer.etherlink.com/api",
        }
      }
    ],
  },
  
};

export default config;
