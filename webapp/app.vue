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
const configurationStore = useConfigurationStore();

const loaded = ref(false);
onMounted(async () => {
  await configurationStore.initial;
  loaded.value = true;
});
</script>
