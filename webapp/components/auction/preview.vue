<template>
  <v-card :loading="pending" height="430" width="100%" :to="`/auctions/${id}`">
    <v-img :src="perfume.imageUrl" height="300" width="100%" cover />
    <v-card-title>{{ perfume.name }}</v-card-title>
    <v-card-text>
      <div class="d-flex flex-row align-center">
        <v-avatar size="32">
          <v-img :src="`/api/users/${seller}/avatar`" />
        </v-avatar>
        <a class="ml-2 text-decoration-none" :href="`https://testnet-explorer.etherlink.com/address/${seller}`"
          target="_blank" @click.stop>{{ sellerName }}</a>
      </div>
      <div class="d-flex flex-column align-end">
        <span class="text-overline" style="line-height: unset"> Last bid </span>
        <span class="font-weight-bold"> {{ lastBid }} $ </span>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { getPerfume } from "~/data/perfumes";

const magicStore = useMagicStore();

const props = defineProps<{
  /* consider inchanged */
  id: number;
}>();

const perfume = computed(() => getPerfume(props.id));

const seller = ref<string>();
const sellerName = ref<string>();
const lastBid = ref<number>();

const pending = ref(true);
onMounted(async () => {
  try {
    const auction = await magicStore.auctionContract.auctions(props.id);
    console.log({ id: props.id, auction: [...auction] });

    const coinTokenAddress = auction[3];
    const coinToken = magicStore.loadERC20(coinTokenAddress);
    const decimals = await coinToken.decimals();

    seller.value = auction[0];
    sellerName.value = toShortAddress(seller.value!);
    lastBid.value = Number(auction[5] / BigInt(10) ** decimals);

    try {
      const user = await $fetch<any>(`/api/users/${seller.value}`);

      const displayName = user.name || user.email;
      if (displayName) {
        sellerName.value = displayName;
      }
    } catch (error) {
      console.error({ error });
    }
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
});
</script>
