<template>
	<v-dialog v-model="state" width="auto">
		<v-card width="25vw">
			<v-card-title class="text-center"> <strong>Congratulation you won the bid</strong> </v-card-title>
			<v-divider thickness="2"></v-divider>
			<v-btn variant="tonal" class="ma-5" rounded="0"> Continue </v-btn>
		</v-card>
	</v-dialog>
</template>

<script setup lang="ts">

const state = defineModel<boolean>("state");

</script>