<template>
  <v-container>
    <v-row>
      <v-col v-if="!magicStore.isConnected" cols="12">
        <auth-login />
      </v-col>
      <v-col v-else cols="12">
        <v-card title="Create your auction">
          <v-card-text>
            <v-form>
              <v-select v-model="selectedId" :items="nftItems" label="NFT">
                <template #append>
                  <v-btn icon variant="flat" @click="refresh">
                    <v-icon>mdi-refresh</v-icon>
                  </v-btn>
                </template>
              </v-select>
              <v-select
                v-model="selectedCoin"
                :items="coinItems"
                label="ERC20"
              />
              <v-date-picker
                v-model="selectedDeadline"
                title="Deadline"
                landscape
                show-week
                show-adjacent-months
              ></v-date-picker>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn :disabled="!canCreate" @click="create"> CREATE </v-btn>
          </v-card-actions>

          <v-overlay
            :model-value="pending"
            class="align-center justify-center"
            contained
            persistent
          >
            <v-progress-circular indeterminate />
          </v-overlay>
        </v-card>
      </v-col>
    </v-row>

    <v-snackbar v-model="snackbar" :timeout="10_000">
      Auction has been
      <a
        :href="`https://testnet-explorer.etherlink.com/tx/${transactionHash}`"
        target="_blank"
        >created</a
      >.

      <template #actions>
        <v-btn color="blue" variant="text" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script setup lang="ts">
import { getPerfume } from "~/data/perfumes";

const magicStore = useMagicStore();
const router = useRouter();

const ids = ref(Array<number>());
const nftItems = computed(() =>
  ids.value.map((id) => {
    const perfume = getPerfume(id);

    return {
      title: perfume.name,
      value: id,
      props: {
        subtitle: `NFT #${id}`,
      },
    };
  })
);

const coins = ref(
  Array<{
    address: string;
    symbol: string;
  }>()
);
const coinItems = computed(() =>
  coins.value.map((coin) => {
    return {
      title: `$${coin.symbol} Token`,
      value: coin.address,
      props: {
        subtitle: coin.address,
      },
    };
  })
);

const selectedId = ref<number>();
const selectedCoin = ref<string>();
const selectedDeadline = ref<Date>(
  (function () {
    const now = new Date();
    now.setMilliseconds(0);
    now.setSeconds(0);
    now.setMinutes(0);
    now.setHours(0);
    now.setDate(now.getDate() + 10);

    return now;
  })()
);

const canCreate = computed(
  () =>
    ![selectedId.value, selectedCoin.value, selectedDeadline.value].includes(
      undefined
    )
);

const pending = ref(false);

const snackbar = ref(false);
const transactionHash = ref("");

async function refresh() {
  if (!magicStore.isConnected) {
    return;
  }

  pending.value = true;
  try {
    {
      const promises = Array<Promise<number>>();
      const count = await magicStore.nftContract.balanceOf(magicStore.address);
      for (let index = BigInt(0); index < count; ++index) {
        promises.push(
          magicStore.nftContract
            .tokenOfOwnerByIndex(magicStore.address, index)
            .then(Number)
        );
      }

      ids.value = await Promise.all(promises);
    }

    {
      coins.value = [
        {
          address: await magicStore.coinContract.getAddress(),
          symbol: await magicStore.coinContract.symbol(),
        },
      ];

      selectedCoin.value = coins.value[0].address;
    }
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}

async function create() {
  pending.value = true;
  try {
    {
      const transaction = await magicStore.nftContract.approve(
        await magicStore.auctionContract.getAddress(),
        BigInt(selectedId.value!),
        {
          gasPrice: await magicStore.getGasPrice(),
        }
      );
      console.log({ transaction });

      const receipt = await transaction.wait();
      console.log({ receipt });
    }

    let auctionId: BigInt;
    {
      const transaction = await magicStore.auctionContract.create(
        BigInt(selectedId.value!),
        selectedCoin.value,
        selectedDeadline.value.getTime(),
        {
          gasPrice: await magicStore.getGasPrice(),
        }
      );
      transactionHash.value = transaction.hash;

      console.log({ transaction });

      const receipt = await transaction.wait();
      snackbar.value = true;

      console.log({ receipt });

      auctionId = receipt.logs[1].args[0];
    }

    console.log({ auctionId });
    router.push(`/auctions/${auctionId}`);
  } catch (error) {
    console.error({ error });
  } finally {
    pending.value = false;
  }
}

watch(
  () => magicStore.isConnected,
  (value: boolean) => {
    if (value) {
      nextTick(refresh);
    }
  },
  {
    immediate: true,
  }
);
</script>
