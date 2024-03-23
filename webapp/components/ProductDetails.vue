<template>
	<v-card class="pa-4" height="100%" width="100%">
		<div class="text-h4 text-wrap mb-2">{{ productTitle }}</div>

		<div>
			<div class="mb-6">by <strong>{{ productAuthor }}</strong></div>
			<div class="d-flex justify-space-between mb-2">
				<span>Current Bid</span>
				<strong>{{ currency }}{{ productCurrentBid }}</strong>
			</div>
			<div class="d-flex justify-space-between mb-2">
				<span>Owned by</span>
				<strong>{{ productCurrentOwner }}</strong>
			</div>
			<v-btn block variant="outlined" rounded class="my-2">Make an offer</v-btn>
		</div>

		<v-divider class="my-4" thickness="2"></v-divider>

		<div class="d-flex align-center mb-4">
			<v-avatar image="https://thispersondoesnotexist.com/" size="80" class="mr-3"></v-avatar>
			<p class="text-h6"><strong>{{ productAuthor }}</strong></p>
		</div>

		<v-divider class="my-4" thickness="2"></v-divider>

		<div>
			<p class="text-h6 mb-2"><strong>Description</strong></p>
			<ExpendableTextView :text="productDescription"></ExpendableTextView>
		</div>
	</v-card>
</template>

<script setup>
defineProps({
	productTitle: {
		type: String,
		required: true,
	},
	productAuthor: {
		type: String,
		required: true,
	},
	productCurrentBid: {
		type: Number,
		required: true,
	},
	productCurrentOwner: {
		type: String,
		required: true,
	},
	productDescription: {
		type: String,
		required: true,
	},
	currency: {
		type: String,
		default: "$",
	},
});
</script>