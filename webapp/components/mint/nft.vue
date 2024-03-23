<template>
  <v-card title="NECTART NFT" subtitle="Get some perfume">
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
      NFT has been
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

async function mint() {
  pending.value = true;

  try {
    const transaction = await magicStore.nftContract.safeMint(
      magicStore.address,
      {
        gasPrice: magicStore.gasPrice,
      }
    );
    transactionHash.value = transaction.hash;

    console.log({ transaction });

    const receipt = await transaction.wait();
    snackbar.value = true;

    console.log({ receipt });
  } finally {
    pending.value = false;
  }
}
</script>
