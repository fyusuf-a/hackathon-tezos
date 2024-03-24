import { defineStore } from "pinia";
import { useConfigurationStore } from "./configuration";
import { loadStripe } from "@stripe/stripe-js";

export const useStripeStore = defineStore("stripeStore", () => {
  const configurationStore = useConfigurationStore();

  const publicKey = computed(() => configurationStore.stripePublicKey);
  const client = computed(() => {
    const client = loadStripe(publicKey.value!);
    return () => client;
  });

  return {
    publicKey,
    client,
  };
});
