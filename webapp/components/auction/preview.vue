<template>
  <v-card :loading="pending" height="430" width="100%" :to="`/auctions/${id}`">
    <v-img :src="perfume.imageUrl" height="300" width="100%" cover />
    <v-card-title>{{ perfume.name }}</v-card-title>
    <v-card-text>
      <div class="d-flex flex-row align-center">
        <v-avatar size="32">
          <v-img
            :src="`https://www.gravatar.com/avatar/${seller}?s=128&d=identicon&r=PG`"
          />
        </v-avatar>
        <a
          class="ml-2 text-decoration-none"
          :href="`https://testnet-explorer.etherlink.com/address/${seller}`"
          target="_blank"
          >{{ shortSeller }}</a
        >
      </div>
      <div class="d-flex flex-column align-end">
        <span class="text-overline" style="line-height: unset"> Last bid </span>
        <span class="font-weight-bold"> {{ lastBid }} $EUR </span>
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
  decimals: number;
}>();

const perfume = computed(() => getPerfume(props.id));

const seller = ref<string>();
const lastBid = ref<number>();

const shortSeller = computed(() => {
  if (!seller.value) {
    return "???";
  }

  const prefix = seller.value.substring(0, 2 + 4);
  const suffix = seller.value.substring(
    seller.value.length - 4,
    seller.value.length
  );
  return `${prefix}...${suffix}`;
});

const pending = ref(true);
onMounted(async () => {
  try {
    const auction = await magicStore.auctionContract.auctions(props.id);
    console.log({ id: props.id, auction: [...auction] });

    const coinTokenAddress = auction[3];
    const coinToken = magicStore.loadERC20(coinTokenAddress);
    const decimals = await coinToken.decimals();

    seller.value = auction[0];
    lastBid.value = Number(auction[5] / BigInt(10) ** decimals);
  } finally {
    pending.value = false;
  }
});
</script>
