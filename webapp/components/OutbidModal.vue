<template>
	<v-dialog v-model="state" width="auto">
		<v-card width="25vw">
			<v-card-title class="text-center">
				<strong>Someone outbid you</strong>
			</v-card-title>

			<v-divider thickness="2"></v-divider>

			<div class="d-flex justify-space-between mt-3 mx-6">
				<span>Your Bid</span>
				<strong>{{ currency }}{{ userBid }}</strong>
			</div>
			<div class="d-flex justify-space-between mt-3 mx-6">
				<span>Current Bid</span>
				<strong>{{ currency }}{{ productCurrentBid }}</strong>
			</div>

			<v-row class="my-2" justify="space-around">
				<v-col cols="12" md="5" align="center">
					<v-btn block variant="tonal" rounded="0"> Make offer </v-btn>
				</v-col>

				<v-col cols="12" md="5" align="center">
					<v-btn block variant="tonal" rounded="0"> Refund </v-btn>
				</v-col>
			</v-row>

		</v-card>
	</v-dialog>
</template>

<script setup lang="ts">

defineProps({
	productCurrentBid: {
		type: Number,
		required: true,
	},
	userBid: {
		type: Number,
		required: true,
	},
	currency: {
		type: String,
		default: "$",
	},
});

const state = defineModel<boolean>("state");

</script>