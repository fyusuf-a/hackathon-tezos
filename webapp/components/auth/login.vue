<template>
  <v-card :title="!centered ? 'Login' : undefined">
    <template v-if="centered">
      <v-card-title class="text-center">
        <strong> Login </strong>
      </v-card-title>
      <v-divider thickness="2" />
    </template>
    <v-card-text>
      <v-form @submit.prevent="login">
        <v-text-field
          v-model="email"
          label="Email"
          hide-details
          :variant="centered ? 'outlined' : undefined"
        />
      </v-form>
    </v-card-text>

    <template v-if="centered">
      <v-btn
        color="black"
        variant="flat"
        theme="dark"
        class="ma-5"
        rounded="0"
        @click.stop="login"
      >
        Continue
      </v-btn>
    </template>
    <v-card-actions v-else class="justify-end">
      <v-btn :disabled="!email" @click="login"> LOGIN </v-btn>
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
</template>

<script setup lang="ts">
import { useStorage } from "@vueuse/core";

const props = withDefaults(
  defineProps<{
    centered?: boolean;
  }>(),
  {
    centered: false,
  }
);

const magicStore = useMagicStore();

const email = useStorage("email", "");
const pending = ref(false);

async function login() {
  pending.value = true;
  try {
    await magicStore.login(email.value);
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}
</script>
