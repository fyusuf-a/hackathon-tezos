<template>
	<v-container>
		<v-img :src="src" aspect-ratio="1" @click="showDialog = true"></v-img>
		<v-dialog v-model="showDialog" :max-width="maxWidth">
			<v-card>
				<v-img :src="src" @click.stop="showDialog = false"></v-img>
			</v-card>
		</v-dialog>
	</v-container>
</template>

<script setup>

const showDialog = ref(false);
const maxWidth = ref(600);

const { src } = defineProps({
	src: {
		type: String,
		required: true,
	},
});
</script>

<style scoped></style>