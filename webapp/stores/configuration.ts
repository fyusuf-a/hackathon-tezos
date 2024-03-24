import { defineStore } from "pinia";

export const useConfigurationStore = defineStore("configurationStore", () => {
  const web3Contracts = ref<{
    usdc: string;
    nectart: string;
    auction: string;
  }>();

  const stripePublicKey = ref<string>();

  const initial = new Promise(async (resolve) => {
    try {
      const configuration = await $fetch<any>(`/api/configuration`);

      web3Contracts.value = configuration.web3Contracts;
      stripePublicKey.value = configuration.stripePublicKey;
    } catch (error) {
      console.error({ error });
    }

    resolve(42);
  });

  return {
    web3Contracts,
    stripePublicKey,
    initial,
  };
});
