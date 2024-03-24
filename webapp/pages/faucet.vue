<template>
  <v-container>
    <v-row>
      <v-col v-if="!magicStore.isConnected" cols="12">
        <auth-login />
      </v-col>
      <template v-else>
        <v-col cols="12">
          <mint-coin />
        </v-col>
        <v-col cols="12">
          <mint-nft />
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();
</script>
