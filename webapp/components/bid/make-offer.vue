<template>
  <v-dialog v-model="model" width="auto">
    <auth-login
      v-if="!magicStore.isConnected"
      style="min-width: 500px"
      centered
    />
    <v-card v-else min-width="500px">
      <v-card-title class="text-center">
        <strong>Make an offer</strong>
      </v-card-title>
      <v-divider thickness="2" />
      <v-card-text>
        <v-tabs v-model="tab" height="40">
          <v-tab size="small">
            <v-icon start>mdi-credit-card</v-icon>
            Credit Card
          </v-tab>
          <v-tab size="small">
            <v-img width="16" height="16" :src="tezos" class="v-icon--start" />
            Web3
          </v-tab>
        </v-tabs>

        <div class="d-flex justify-space-between mt-8 mb-12">
          <span>Current offer</span>
          <strong>${{ bestBid }}</strong>
        </div>

        <v-window v-model="tab">
          <v-window-item>
            <v-form>
              <v-text-field
                v-model="name"
                label="Name"
                variant="outlined"
                class="mt-1"
              />
              <v-text-field
                v-model="email"
                label="Email"
                type="email"
                hide-details
                variant="outlined"
              />
            </v-form>
          </v-window-item>
          <v-window-item>
            <div class="d-flex justify-space-between mx-1">
              <span>Account debited</span>
              <address-avatar :address="magicStore.address" short />
            </div>
          </v-window-item>
        </v-window>
      </v-card-text>

      <v-card-text>
        <v-text-field
          prepend-inner-icon="mdi-currency-usd"
          v-model="amountModel"
          label="Amount"
          variant="outlined"
          required
          class="mb-3"
          hide-details
          type="number"
          :min="bestBid + 1"
          step="1"
        >
        </v-text-field>
      </v-card-text>

      <div class="d-flex justify-space-between mx-6 mb-3">
        <span>Total</span>
        <strong>${{ amount }}</strong>
      </div>

      <v-btn
        color="black"
        variant="flat"
        theme="dark"
        class="ma-5"
        rounded="0"
        @click="doBid"
      >
        Make offer
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
  <bid-stripe
    v-if="stripeDialog"
    v-model="stripeDialog"
    :bid-id="bid.id"
    :payment-intent-id="bid.stripePaymentIntentId"
    :client-secret="bid.stripeClientSecret"
    @success="emit('success')"
  />
</template>

<script setup lang="ts">
import tezos from "~/assets/tezos.svg";

const magicStore = useMagicStore();

const props = defineProps<{
  bestBid: number;
  coinContractAddress: string;
  auctionId: number;
}>();

const emit = defineEmits(["success"]);

const name = ref("");
const email = ref("");

const model = defineModel<boolean>();
const amount = ref(0);

const amountModel = computed({
  get: () => amount.value,
  set(value: string | number) {
    let parsed = parseInt(value as string);
    if (!isNaN(parsed)) {
      amount.value = parsed;
    }
  },
});

watch(
  () => props.bestBid,
  (value) => {
    console.log({ value });
    amount.value = 1 + Math.ceil(value * 1.1);
  },
  {
    immediate: true,
  }
);

const tab = ref(0);

const pending = ref(false);

const bid = ref<any>();
const stripeDialog = ref(false);

async function bidViaCreditCard() {
  try {
    await $authFetch(`/api/users/@me`, {
      method: "PATCH",
      body: {
        name: name.value,
      },
    });
  } catch (error) {
    console.log({ error });
  }

  try {
    await $authFetch(`/api/users/@me`, {
      method: "PATCH",
      body: {
        email: email.value,
      },
    });
  } catch (error) {
    console.log({ error });
  }

  bid.value = await $authFetch<any>(`/api/bids`, {
    method: "POST",
    body: {
      auctionId: props.auctionId,
      amount: amount.value,
    },
  });

  stripeDialog.value = true;

  console.log({ bid });
}

async function bidViaEthereum() {
  const coinContract = magicStore.loadERC20(props.coinContractAddress);
  const decimals = await coinContract.decimals();
  const value = BigInt(10) ** decimals * BigInt(amount.value);

  {
    const transaction = await coinContract.approve(
      await magicStore.auctionContract.getAddress(),
      value,
      {
        gasPrice: await magicStore.getGasPrice(),
      }
    );
    console.log({ transaction });

    const receipt = await transaction.wait();
    console.log({ receipt });
  }

  {
    const transaction = await magicStore.auctionContract.bid(
      props.auctionId,
      value,
      {
        gasPrice: await magicStore.getGasPrice(),
      }
    );
    console.log({ transaction });

    const receipt = await transaction.wait();
    console.log({ receipt });
  }

  emit("success");
}

async function doBid() {
  pending.value = true;
  try {
    if (tab.value == 0) {
      await bidViaCreditCard();
    } else {
      await bidViaEthereum();
    }

    model.value = false;
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}

async function refresh() {
  if (!magicStore.isConnected) {
    return;
  }

  pending.value = true;
  try {
    const user = await $authFetch<any>(`/api/users/@me`);
    name.value = user.name;
    email.value = user.email;
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}

watch(
  () => magicStore.isConnected,
  (value) => {
    if (value) {
      refresh();
    }
  },
  {
    immediate: true,
  }
);

watch(model, (value) => {
  if (value) {
    refresh();
  }
});
</script>
