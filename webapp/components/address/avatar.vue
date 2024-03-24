<template>
  <div class="d-flex flex-row align-center">
    <v-avatar size="32">
      <v-img :src="`/api/users/${address}/avatar`" />
    </v-avatar>
    <a
      class="ml-2 text-decoration-none text-black"
      :href="`https://testnet-explorer.etherlink.com/address/${address}`"
      target="_blank"
      @click.stop
      >{{ display }}</a
    >
  </div>
</template>

<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    address: string;
    short?: boolean;
  }>(),
  {
    short: false,
  }
);

const display = computed(() => props.short ? toShortAddress(props.address) : props.address)
</script>
