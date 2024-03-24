<template>
  <v-container>
    <v-row>
      <v-col v-for="x in 5" :key="x" cols="12" md="6" lg="4">
        <v-card height="430" width="100%">
          <v-img
            :src="getPerfume(x)?.imageUrl"
            height="300"
            width="100%"
            cover
          />
          <v-card-title>{{ getPerfume(x)?.name }}</v-card-title>
          <v-card-text>
            <div class="d-flex flex-row align-center">
              <v-avatar size="32">
                <v-img
                  src="https://www.gravatar.com/avatar/qsd?s=128&d=identicon&r=PG"
                />
              </v-avatar>
              <a class="ml-2">Hello World</a>
            </div>
            <div class="d-flex flex-column align-end">
              <span class="text-overline" style="line-height: unset">
                Last bid
              </span>
              <span class="font-weight-bold"> 1324 $EUR </span>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" md="6" lg="4">
        <v-card height="350" width="100%" to="/auctions/new">
          <div
            class="d-flex flex-column align-center justify-center"
            style="height: 300px; width: 100%"
          >
            <v-icon size="128">mdi-plus</v-icon>
          </div>
          <v-card-text>
            <span>Create your own auction!</span>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { getPerfume } from "~/data/perfumes";
</script>
