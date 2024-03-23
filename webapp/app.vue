<template>
  <v-app>
    <NuxtLayout v-if="loaded">
      <NuxtPage />
    </NuxtLayout>
    <v-main v-else class="d-flex align-center justify-center">
      <v-progress-circular indeterminate />
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();

const loaded = ref(false);
onMounted(async () => {
  await magicStore.initial;
  loaded.value = true;
});
</script>
