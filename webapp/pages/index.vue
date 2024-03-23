<template>
  <div>
    <div style="display: flex; flex-direction: column; margin-top: 20px">
      <input v-model="email" type="email" placeholder="Email" />
      <button v-if="magicStore.isConnected" @click="() => magicStore.logout()">
        Logout
      </button>
      <button v-else @click="() => magicStore.login(email)">
        Login
      </button>
      {{ magicStore.isConnected ? "Connected" : "Not connected" }}
    </div>
  </div>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();
const email = ref("");
</script>
