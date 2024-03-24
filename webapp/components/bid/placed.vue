<template>
  <v-dialog v-model="model" width="auto">
    <v-card min-width="500px">
      <v-card-title class="text-center">
        <strong>Congratulation</strong>
      </v-card-title>
      <v-divider thickness="2" />
      <v-card-text class="text-center">
        You are now the highest bidder!
      </v-card-text>
      <v-btn
        class="ma-5"
        rounded="0"
        color="black"
        variant="flat"
        theme="dark"
        @click="model = false"
      >
        Continue
      </v-btn>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
const model = defineModel<boolean>();
</script>
