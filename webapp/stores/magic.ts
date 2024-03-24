import { defineStore } from "pinia";
import { ethers } from "ethers";
import { Magic } from "magic-sdk";

import USDCToken from "~/contracts/USDCToken.json";
import NectartToken from "~/contracts/NectartToken.json";
import AuctionContract from "~/contracts/AuctionContract.json";
import { useConfigurationStore } from "./configuration";

const customNodeOptions = {
  rpcUrl: window.origin + "/ghostnet/",
  chainId: 128123,
};

const magic = new Magic(import.meta.env.VITE_MAGIC_PUBLISHABLE_KEY, {
  network: customNodeOptions,
});

export const useMagicStore = defineStore("magicStore", () => {
  const configurationStore = useConfigurationStore();

  const addresses = computed(() => configurationStore.web3Contracts);

  const provider = ref<() => ethers.BrowserProvider>(
    () => new ethers.BrowserProvider(magic.rpcProvider)
  );
  const signer = ref<() => ethers.JsonRpcSigner>();
  const did = ref<string>();
  const address = ref<string>();
  const isReadOnly = computed<boolean>(() => !signer.value);
  const isConnected = computed<boolean>(() => !!did.value);

  const login = async (email: string) => {
    const didToken = (await magic.auth.loginWithEmailOTP({
      email,
      showUI: true,
    }))!;

    const signerInstance = await provider.value().getSigner();
    signer.value = () => signerInstance;
    address.value = signerInstance.address;

    did.value = didToken;
  };

  const logout = async () => {
    await magic.user.logout();
    did.value = undefined;
    signer.value = undefined;
    address.value = undefined;
  };

  function loadContract(
    abi: ethers.Interface,
    address: string
  ): ethers.Contract {
    return new ethers.Contract(
      address,
      abi,
      signer.value?.() ?? provider.value()
    );
  }

  function loadERC20(address: string) {
    return loadContract(USDCToken.abi as any, address);
  }

  const coinContract = computed(() => loadERC20(addresses.value?.usdc!));

  const nftContract = computed(() =>
    loadContract(NectartToken.abi as any, addresses.value?.nectart!)
  );

  const auctionContract = computed(() =>
    loadContract(AuctionContract.abi as any, addresses.value?.auction!)
  );

  async function getGasPrice() {
    return (await provider.value().getFeeData()).gasPrice! * BigInt(2);
  }

  return {
    did,
    provider,
    signer,
    address,
    login,
    logout,
    getGasPrice,
    isReadOnly,
    isConnected,
    loadERC20,
    coinContract,
    nftContract,
    auctionContract,
  };
});
