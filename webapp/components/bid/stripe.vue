<template>
  <v-dialog v-model="model" width="auto">
    <v-card min-width="500px">
      <v-card-title class="text-center">
        <strong>Payment</strong>
      </v-card-title>
      <v-divider thickness="2" />
      <v-card-text>
        <div id="stripe-payment-element" style="min-height: 224px"></div>
      </v-card-text>
      <v-btn
        color="black"
        variant="flat"
        theme="dark"
        class="ma-5"
        rounded="0"
        @click="pay"
      >
        Pay
      </v-btn>

      <v-overlay
        :model-value="pending"
        class="align-center justify-center"
        contained
        persistent
      >
        <v-progress-circular indeterminate />
      </v-overlay>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
const stripeStore = useStripeStore();
import type { StripeElements } from "@stripe/stripe-js";

const props = defineProps<{
  bidId: number;
  paymentIntentId: string;
  clientSecret: string;
}>();

const emit = defineEmits(["success"]);

const model = defineModel<boolean>();
const pending = ref(false);

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
  pending.value = true;

  try {
    const client = await stripeStore.client();
    const { error } = await client!.confirmPayment({
      elements: elements.value!,
      redirect: "if_required",
    });

    console.log({ error });

    while (true) {
      const bid = await $authFetch<any>(`/api/bids/${props.bidId}`);
      if (bid.transactionHash) {
        break;
      }

      await sleep(1_000);
    }

    model.value = false;
    emit("success");
  } catch (error) {
    console.error({ error });
  } finally {
    pending.value = false;
  }
}
</script>
