<template>
	<v-container>
		<v-img :src="src" aspect-ratio="1" class="elevation-2" @click="showDialog = true"></v-img>

		<v-dialog v-model="showDialog" max-width="600">
			<v-card>
				<v-img :src="src" @click.stop="showDialog = false" aspect-ratio="1"></v-img>
			</v-card>
		</v-dialog>
	</v-container>
</template>

<script>
export default {
	props: {
		src: {
			type: String,
			required: true,
		},
	},
	data: () => ({
		showDialog: false,
	}),
}
</script>

<style scoped></style>