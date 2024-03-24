<template>
  <v-container v-if="initialRefresh" class="d-flex align-center justify-center">
    <v-progress-circular indeterminate />
  </v-container>
  <v-container v-else style="height: 100vh" class="d-flex align-center flex">
    <v-row>
      <v-col cols="12" md="9" sm="6">
        <v-row>
          <v-col class="flex-grow-0 flex-shrink-1" cols="2"></v-col>
          <v-col class="flex-grow-1 flex-shrink-0">
            <ExpendableImageView :src="perfume.imageUrl" />
          </v-col>
          <v-col class="flex-grow-0 flex-shrink-1" cols="2"></v-col>
        </v-row>
      </v-col>
      <v-col cols="12" md="3" sm="6" outlined>
        <v-card class="pa-4" height="100%" width="100%">
          <div class="text-h4 text-wrap mb-2">{{ perfume.name }}</div>
          <div>
            <v-btn @click="placed = true">qsd</v-btn>
            <div class="mb-6">
              by <strong>{{ productAuthor }}</strong>
            </div>
            <div class="d-flex justify-space-between mb-2">
              <span>Best bid</span>
              <strong>${{ bestBid }}</strong>
            </div>
            <div class="d-flex justify-space-between mb-2">
              <span>Best bidder</span>
              <strong>{{ bestBidderName }}</strong>
            </div>
            <v-btn
              block
              variant="outlined"
              rounded
              class="my-2"
              @click="offerDialog = true"
            >
              Make an offer
            </v-btn>
          </div>
          <v-divider class="my-4" thickness="2" />
          <div class="d-flex align-center mb-4">
            <v-avatar :image="jose" size="80" class="mr-3" />
            <p class="text-h6">
              <strong>{{ productAuthor }}</strong>
            </p>
          </div>
          <v-divider class="my-4" thickness="2" />
          <div>
            <p class="text-h6 mb-2"><strong>Description</strong></p>
            <ExpendableTextView
              text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus eget sapien fermentum aliquam. Nullam nec nunc nec nunc.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus eget sapien fermentum aliquam. Nullam nec nunc nec nunc."
            />
          </div>
        </v-card>
      </v-col>
    </v-row>
    <bid-make-offer
      v-model="offerDialog"
      :best-bid="bestBid"
      :coin-contract-address="coinContractAddress"
      :auction-id="auctionId"
      @success="placed = true"
    />
    <bid-placed v-model="placed" />
  </v-container>
</template>

<script setup lang="ts">
import { getPerfume } from "~/data/perfumes";

import jose from "~/assets/jose.jpg";

const magicStore = useMagicStore();

definePageMeta({
  layout: "empty",
});

const route = useRoute();

const auctionId = computed(() => parseInt(route.params.auctionId) || 0);
const perfume = computed(() => getPerfume(auctionId.value));

const seller = ref<string>();
const tokenId = ref<number>();
const deadline = ref<Date>();
const coinContractAddress = ref<string>();

const bestBidder = ref<string>();
const bestBid = ref<number>();
const bestBidIsVirtual = ref<boolean>();

const claimedNft = ref<boolean>();
const claimedCoin = ref<boolean>();

const sellerName = ref<string>();
const bestBidderName = ref<string>();
const decimals = ref<number>();

const offerDialog = ref(false);

const initialRefresh = ref(true);
const pending = ref(false);
async function refresh() {
  pending.value = true;
  try {
    const auction = await magicStore.auctionContract.auctions(auctionId.value);
    console.log({ id: auctionId.value, auction: [...auction] });

    const coinTokenAddress = auction[3];
    const coinToken = magicStore.loadERC20(coinTokenAddress);
    decimals.value = Number(await coinToken.decimals());

    seller.value = auction[0];
    tokenId.value = Number(auction[1]);
    deadline.value = new Date(Number(auction[2]));
    coinContractAddress.value = auction[3];
    bestBidder.value = auction[4];
    bestBid.value = Number(auction[5] / BigInt(10) ** BigInt(decimals.value));
    bestBidIsVirtual.value = auction[6];
    claimedNft.value = auction[7];
    claimedCoin.value = auction[8];

    try {
      const user = await $fetch<any>(`/api/users/${seller.value}`);

      const displayName = user.name || user.email;
      if (displayName) {
        sellerName.value = displayName;
      } else {
        sellerName.value = toShortAddress(seller.value);
      }
    } catch (error) {
      console.error({ error });
      sellerName.value = toShortAddress(seller.value);
    }

    try {
      const user = await $fetch<any>(`/api/users/${bestBidder.value}`);

      const displayName = user.name || user.email;
      if (displayName) {
        bestBidderName.value = displayName;
      } else {
        bestBidderName.value = toShortAddress(bestBidder.value);
      }
    } catch (error) {
      console.error({ error });
      bestBidderName.value = toShortAddress(bestBidder.value);
    }
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
    initialRefresh.value = false;
  }
}

const placed = ref(false);

onMounted(refresh);
useIntervalFn(refresh, 2000);

const productAuthor = "José Delbo";
</script>

<style>
/* Custom styles here */
</style>
