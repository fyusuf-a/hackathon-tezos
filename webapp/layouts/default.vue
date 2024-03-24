<template>
  <v-layout class="rounded rounded-md">
    <v-app-bar title="NectArt">
      <template #prepend>
        <v-app-bar-nav-icon
          slot="prepend"
          variant="text"
          @click.stop="drawer = !drawer"
        />
      </template>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer">
      <v-list>
        <v-list-item prepend-icon="mdi-view-dashboard" title="Home" to="/" />
        <v-list-item prepend-icon="mdi-palette" title="Perfumes" to="/auctions" />
        <v-list-item prepend-icon="mdi-account" title="Account" to="/account" />
        <v-list-item prepend-icon="mdi-faucet" title="Faucet" to="/faucet" />
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <slot />
    </v-main>
  </v-layout>
</template>

<script setup lang="ts">
const drawer = ref(true);
</script>
