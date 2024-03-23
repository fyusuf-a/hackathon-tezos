<template>
  <v-card title="Login">
    <v-card-text>
      <v-form @submit.prevent="login">
        <v-text-field v-model="email" label="Email" hide-details />
      </v-form>
    </v-card-text>
    <v-card-actions class="justify-end">
      <v-btn :disabled="!email" @click="login"> LOGIN </v-btn>
    </v-card-actions>

    <v-overlay
      :model-value="pending"
      class="align-center justify-center"
      contained
    >
      <v-progress-circular indeterminate />
    </v-overlay>
  </v-card>
</template>

<script setup lang="ts">
import { useStorage } from "@vueuse/core";

const magicStore = useMagicStore();

const email = useStorage("email", "");
const pending = ref(false);

async function login() {
  pending.value = true;
  try {
    await magicStore.login(email.value);
  } finally {
    pending.value = false;
  }
}
</script>
