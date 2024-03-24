<template>
	<v-dialog v-model="state" width="auto">
		<v-card width="25vw">
			<v-card-title class="text-center"><strong> Make an offer </strong></v-card-title>

			<v-divider thickness="2"></v-divider>

			<v-card-text>
				<v-text-field v-model="email" label="Email" variant="outlined" required class="mt-4"
					:rules="[rules.required, rules.email]"></v-text-field>
			</v-card-text>

			<v-divider thickness="2"></v-divider>

			<v-btn variant="tonal" class="ma-5" rounded="0" @click.stop="onBottomClick()">
				Continue
			</v-btn>

		</v-card>
	</v-dialog>
</template>

<script setup lang="ts">

const state = defineModel<boolean>("state");
const userEmail = defineModel<string>("userEmail");

const email = ref("");
const rules = {
	required: (v: string) => !!v || "Required",
	email: (v: string) => /.+@.+\..+/.test(v) || "E-mail must be valid",
};

function onBottomClick() {
	if (email.value && rules.email(email.value)) {
		userEmail.value = email.value;
		state.value = false;
	} else {
		console.log("Invalid email");
		email.value = "";
	}
}

</script>