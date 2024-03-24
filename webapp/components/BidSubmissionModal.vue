<template>
	<v-dialog v-model="state" width="auto">
		<v-card width="25vw">
			<v-card-title class="text-center">
				<strong>Make an Offer</strong>
			</v-card-title>

			<v-divider thickness="2"></v-divider>

			<div class="d-flex justify-space-between mt-3 mx-6">
				<span>Current offer</span>
				<strong>{{ currency }}{{ productCurrentBid }}</strong>
			</div>
			<div class="d-flex justify-space-between mt-3 mx-6">
				<span>Offer amount</span>
			</div>

			<v-card-text>
				<v-text-field prepend-inner-icon="mdi-currency-usd" v-model="amount" label="Amount" variant="outlined"
					required class="mt-3 mb-3" hide-details single-line type="number">
				</v-text-field>
			</v-card-text>

			<div class=" d-flex justify-space-between mx-6 mb-3">
				<span>Total</span>
				<strong>{{ currency }}{{ amount }}</strong>
			</div>

			<v-divider thickness="2"></v-divider>

			<v-btn variant="tonal" class="ma-5" rounded="0">
				Make offer
			</v-btn>

		</v-card>
	</v-dialog>
</template>

<script setup lang="ts">

defineProps({
	productCurrentBid: {
		type: Number,
		required: true,
	},
	currency: {
		type: String,
		default: "$",
	},
});

const state = defineModel<boolean>("state");
const userAmount = defineModel<string>("userAmount");

const amount = ref(0);

</script>