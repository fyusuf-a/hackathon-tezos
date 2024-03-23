import { defineStore } from "pinia";
import { ethers } from "ethers";
import { Magic } from "magic-sdk";

import USDCToken from "~/contracts/USDCToken.json";
import NectartToken from "~/contracts/NectartToken.json";
import AuctionContract from "~/contracts/AuctionContract.json";

const customNodeOptions = {
  rpcUrl: window.origin + "/ghostnet/",
  chainId: 128123,
};

const magic = new Magic(import.meta.env.VITE_MAGIC_PUBLISHABLE_KEY, {
  network: customNodeOptions,
});

export const useMagicStore = defineStore("magicStore", () => {
  const addresses = ref<{
    usdc: string;
    nectart: string;
    auction: string;
  }>();

  const provider = ref<() => ethers.BrowserProvider>(() => new ethers.BrowserProvider(magic.rpcProvider));
  const signer = ref<() => ethers.JsonRpcSigner>();
  const did = ref<string>();
  const address = ref<string>();
  const isReadOnly = computed<boolean>(() => !signer.value);
  const isConnected = computed<boolean>(() => !!did.value);
  const gasPrice = ref<BigInt>(BigInt(0))

  const login = async (email: string) => {
    did.value =
      (await magic.auth.loginWithEmailOTP({ email, showUI: true })) ||
      undefined;
    const signerInstance = await provider.value().getSigner();

    signer.value = () => signerInstance;
    address.value = signerInstance.address;

    gasPrice.value = (await provider.value().getFeeData()).gasPrice!
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
    return new ethers.Contract(address, abi, signer.value?.() ?? provider.value());
  }

  const coinContract = computed(() =>
    loadContract(USDCToken.abi as any, addresses.value?.usdc!)
  );

  const nftContract = computed(() =>
    loadContract(NectartToken.abi as any, addresses.value?.nectart!)
  );

  const auctionContract = computed(() =>
    loadContract(AuctionContract.abi as any, addresses.value?.auction!)
  );

  const initial = new Promise(async (resolve) => {
    try {
      addresses.value = await $fetch(`/api/web3/contracts`);
    } catch (error) {
      console.error({ error });
    }

    resolve(42);
  });

  async function loginAnd<T>(action: () => Promise<T>): Promise<T> {
    if (!isConnected.value) {
      await login("caceresenzo1502@gmail.com");
      // await login(prompt("email") as string)
    }

    return action();
  }

  return {
    did,
    provider,
    signer,
    address,
    gasPrice,
    login,
    loginAnd,
    logout,
    isReadOnly,
    isConnected,
    coinContract,
    nftContract,
    auctionContract,
    initial,
  };
});
