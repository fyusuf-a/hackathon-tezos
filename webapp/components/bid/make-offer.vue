<template>
  <v-dialog v-model="model" width="auto">
    <auth-login
      v-if="!magicStore.isConnected"
      style="min-width: 500px"
      centered
    />
    <v-card v-else ref="cardRef" min-width="500px">
      <v-card-title class="text-center">
        <strong>Make an Offer</strong>
      </v-card-title>

      <v-divider thickness="2" />

      <v-tabs v-model="tab" height="40">
        <v-tab size="small">
          <v-icon start>mdi-credit-card</v-icon>
          Credit Card
        </v-tab>
        <v-tab size="small">
          <v-icon start>mdi-ethereum</v-icon>
          Ethereum
        </v-tab>
      </v-tabs>

      <div class="d-flex justify-space-between mt-3 mx-6">
        <span>Current offer</span>
        <strong>${{ bestBid }}</strong>
      </div>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item>
            <v-form>
              <v-text-field v-model="name" label="Name" />
              <v-text-field
                v-model="email"
                label="Email"
                type="email"
                hide-details
              />
            </v-form>
          </v-window-item>
          <v-window-item>
            <address-avatar :address="magicStore.address" />
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
          class="mt-3 mb-3"
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

      <v-divider thickness="2" />

      <v-btn variant="tonal" class="ma-5" rounded="0" @click="doBid">
        Make offer
      </v-btn>

      <v-overlay
        :model-value="pending"
        class="align-center justify-center"
        contained
      >
        <v-progress-circular indeterminate />
      </v-overlay>

      <v-snackbar v-model="snackbar" :timeout="10_000">
        Coins have been
        <a
          :href="`https://testnet-explorer.etherlink.com/tx/${transactionHash}`"
          target="_blank"
          >minted</a
        >.

        <template v-slot:actions>
          <v-btn color="blue" variant="text" @click="snackbar = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </v-card>
  </v-dialog>
  <bid-stripe
    v-if="stripeDialog"
    v-model="stripeDialog"
    :payment-intent-id="bid.stripePaymentIntentId"
    :client-secret="bid.stripeClientSecret"
  />
</template>

<script setup lang="ts">
import party from "party-js";

const magicStore = useMagicStore();

const cardRef = ref<{ $el: HTMLElement }>();

const props = defineProps<{
  bestBid: number;
  coinContractAddress: string;
  auctionId: number;
}>();

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

const snackbar = ref(false);
const transactionHash = ref("");

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
    transactionHash.value = transaction.hash;

    console.log({ transaction });

    const receipt = await transaction.wait();
    snackbar.value = true;

    console.log({ receipt });
  }
}

async function doBid() {
  pending.value = true;
  try {
    if (tab.value == 0) {
      await bidViaCreditCard();
    } else {
      await bidViaEthereum();
    }

    party.confetti(cardRef.value?.$el!, {
      count: party.variation.range(20, 40),
    });

    // model.value = false;
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
