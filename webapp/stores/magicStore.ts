import { defineStore } from 'pinia';
import { ethers } from 'ethers';
import { Magic } from 'magic-sdk';

const customNodeOptions = {
  rpcUrl: 'https://node.ghostnet.etherlink.com',
  chainId: 128123,
};

const magic = new Magic(import.meta.env.VITE_MAGIC_PUBLISHABLE_KEY, {
  network: customNodeOptions,
});

export const useMagicStore = defineStore('magicStore', () => {
  const provider = new ethers.BrowserProvider(magic.rpcProvider);
  const signer = ref<ethers.JsonRpcSigner>();
  const did = ref<string | null>();
  const isReadOnly = computed<boolean>(() => !signer.value);
  const isConnected = computed<boolean>(() => !!did.value);

  const loginWithEmail = async (email: string) => {
    try {
      did.value = await magic.auth.loginWithEmailOTP({ email, showUI: true });
    } catch (error) {
      console.error(error);
    }
  }

  const logout = async () => {
    await magic.user.logout();
    did.value = null;
  }

  function contract(abi: ethers.Interface, address: string): ethers.Contract {
    return new ethers.Contract(
      address,
      abi,
      signer.value ? signer.value : provider,
    );
  }

  return {
    signer,
    contract,
    did,
    isReadOnly,
    loginWithEmail,
    logout,
    isConnected,
  };
});