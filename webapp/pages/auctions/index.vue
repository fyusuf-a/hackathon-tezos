<template>
  <v-container>
    <v-row>
      <v-col v-for="id in ids" :key="id" cols="12" md="6" lg="4">
        <auction-preview :id="id" :key="id" />
      </v-col>
      <v-col cols="12" md="6" lg="4">
        <auction-new />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();

const ids = ref(Array<number>());

async function refresh() {
  try {
    const auctionIds = Array<number>();
    const count = await magicStore.auctionContract.auctionsCount();
    for (let index = BigInt(0); index < count; ++index) {
      auctionIds.push(Number(index));
    }

    ids.value = auctionIds.reverse();
  } catch (error) {
    console.error({ error });
  }
}

onMounted(refresh);
</script>
