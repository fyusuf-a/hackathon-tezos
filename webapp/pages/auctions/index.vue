<template>
  <v-container>
    <v-row>
      <template v-if="pending">
        <v-col v-for="x in 6" :key="x" cols="12" md="6" lg="4">
          <v-skeleton-loader type="card-avatar" />
        </v-col>
      </template>
      <template v-else>
        <v-col v-for="id in ids" :key="id" cols="12" md="6" lg="4">
          <auction-preview :id="id" :key="id" />
        </v-col>
        <v-col cols="12" md="6" lg="4">
          <auction-new />
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();

const pending = ref(true);
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
  } finally {
    pending.value = false;
  }
}

onMounted(refresh);
</script>
