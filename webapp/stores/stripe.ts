import { defineStore } from "pinia";
import { useConfigurationStore } from "./configuration";

export const useStripeStore = defineStore("stripeStore", () => {
  const configurationStore = useConfigurationStore();

  const publicKey = computed(() => configurationStore.stripePublicKey);

  return {
    publicKey,
  };
});
