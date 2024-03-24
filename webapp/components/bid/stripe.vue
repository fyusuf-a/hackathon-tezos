<template>
  <v-dialog v-model="model" width="auto">
    <v-card min-width="500px">
      <v-card-title class="text-center">
        <strong>Payment</strong>
      </v-card-title>

      <v-divider thickness="2" />
      <v-card-text>
        <div id="stripe-payment-element"></div>
      </v-card-text>

      <v-divider thickness="2" />

      <v-btn variant="tonal" class="ma-5" rounded="0" @click="pay"> Pay </v-btn>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
const stripeStore = useStripeStore();
import type { StripeElements } from "@stripe/stripe-js";

const props = defineProps<{
  paymentIntentId: string;
  clientSecret: string;
}>();

const model = defineModel<boolean>();

const elements = ref<StripeElements>();
const paymentElement = ref();

onMounted(async () => {
  const client = await stripeStore.client()!;
  elements.value = await client!.elements({
    clientSecret: props.clientSecret,
  });

  paymentElement.value = await elements.value!.create("payment");
  console.log({ el: paymentElement.value });
  await paymentElement.value!.mount("#stripe-payment-element");
});

async function pay() {
  const client = await stripeStore.client();
  const { error } = await client!.confirmPayment({
    elements: elements.value!,
    redirect: "if_required"
  });

  console.log({ error });
}
</script>
