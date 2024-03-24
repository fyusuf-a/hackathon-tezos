<template>
  <div class="d-flex flex-row align-center">
    <v-avatar size="32">
      <v-img :src="`/api/users/${address}/avatar`" />
    </v-avatar>
    <a
      class="ml-2 text-decoration-none"
      :href="`https://testnet-explorer.etherlink.com/address/${address}`"
      target="_blank"
      @click.stop
      >{{ address }}</a
    >
  </div>
</template>

<script setup lang="ts">
defineProps<{
  address: string;
}>();
</script>
