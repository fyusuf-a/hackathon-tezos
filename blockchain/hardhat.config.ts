import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import "@nomicfoundation/hardhat-verify";
import dotenv from "dotenv";

dotenv.config()
if (!process.env.PRIVATE_KEY) {
  console.warn("PRIVATE_KEY is not set")
  process.env.PRIVATE_KEY = "0x1111111111111111111111111111111111111111111111111111111111111111"
}
if (!process.env.ETHERSCAN_API_KEY) {
  console.warn("ETHERSCAN_API_KEY is not set")
}

const config: HardhatUserConfig = {
  solidity: "0.8.24",
  networks: {
    'etherlink-testnet': {
      url: 'https://node.ghostnet.etherlink.com',
      chainId: 128123,
      accounts: [process.env.PRIVATE_KEY],
    },
    'sepolia': {
      url: 'https://sepolia.infura.io/v3/e1aa95990453490d8c334f88a5661da1',
      chainId: 11155111,
      accounts: [process.env.PRIVATE_KEY],
    },
  },
  etherscan: {
    enabled: true,
    apiKey: process.env.ETHERSCAN_API_KEY,
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
