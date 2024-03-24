<template>
  <v-container>
    <v-row>
      <v-col v-if="!magicStore.isConnected" cols="12">
        <auth-login />
      </v-col>
      <template v-else>
        <v-col cols="12">
          <v-card title="Account">
            <v-card-text>
              <v-form>
                <v-text-field v-model="name" label="Name" />
                <v-text-field v-model="email" label="Email" type="email" />
              </v-form>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn @click="update"> UPDATE </v-btn>
            </v-card-actions>

            <v-overlay
              :model-value="pending"
              class="align-center justify-center"
              contained
            >
              <v-progress-circular indeterminate />
            </v-overlay>
          </v-card>
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
const magicStore = useMagicStore();

const name = ref("");
const email = ref("");

const pending = ref(true);

async function update() {
  pending.value = true;
  try {
    try {
      await $authFetch(`/api/users/@me`, {
        method: "PATCH",
        body: {
          name: name.value,
        },
      });
    } catch (error) {
      console.log({ error });
    }

    try {
      await $authFetch(`/api/users/@me`, {
        method: "PATCH",
        body: {
          email: email.value,
        },
      });
    } catch (error) {
      console.log({ error });
    }
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}

async function refresh() {
  if (!magicStore.isConnected) {
    return;
  }

  pending.value = true;
  try {
    const user = await $authFetch<any>(`/api/users/@me`);
    name.value = user.name;
    email.value = user.email;
  } catch (error) {
    console.log({ error });
  } finally {
    pending.value = false;
  }
}

watch(
  () => magicStore.isConnected,
  (value) => {
    if (value) {
      nextTick(refresh);
    }
  },
  {
    immediate: true,
  }
);
</script>
