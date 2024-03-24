<template>
  <v-card
    title="USDC Token"
    :subtitle="`Current amount: ${currentAmount} USDC`"
  >
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="amountModel"
          type="number"
          step="1"
          min="1"
          label="Amount"
          hide-details
        />
      </v-form>
    </v-card-text>
    <v-card-actions class="justify-end">
      <v-btn @click="mint"> MINT </v-btn>
    </v-card-actions>

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
</template>

<script setup lang="ts">
const magicStore = useMagicStore();

const pending = ref(false);

const snackbar = ref(false);
const transactionHash = ref("");

const amount = ref(100);

const amountModel = computed({
  get: () => amount.value,
  set(value: string | number) {
    let parsed = parseInt(value as string);
    if (!isNaN(parsed)) {
      amount.value = parsed;
    }
  },
});

async function mint() {
  pending.value = true;

  try {
    const decimals = await magicStore.coinContract.decimals();
    const value = BigInt(10) ** decimals * BigInt(amount.value);

    const transaction = await magicStore.coinContract.mint(
      magicStore.address,
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
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }

  refreshCurrentAmount();
}

const currentAmount = ref(BigInt(0));
async function refreshCurrentAmount() {
  const decimals = await magicStore.coinContract.decimals();
  const amount = await magicStore.coinContract.balanceOf(magicStore.address);
  currentAmount.value = amount / BigInt(10) ** decimals;
}

onMounted(refreshCurrentAmount);
</script>
