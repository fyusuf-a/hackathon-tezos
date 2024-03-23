<template>
	<div>
		<!--
			<div>
				<div>
					{{ truncatedText }}
					<v-expand-transition>
						<div v-show="!isTruncated">
							{{ textEnd }}
						</div>
					</v-expand-transition>
				</div>

				<div text @click="toggleText" class="mt-3 view-toggle">
					<strong>{{ isTruncated ? 'View More' : 'View Less' }}</strong>
				</div>
			</div>
		-->
		<transition v-if="isTruncated" name="fade">
			<div>
				{{ truncatedText }}
			</div>
		</transition>
		<transition v-else name="fade">
			<div>
				{{ text }}
			</div>
		</transition>
		<div text @click="toggleText" class="mt-3 view-toggle">
			<strong>{{ isTruncated ? 'View More' : 'View Less' }}</strong>
		</div>
	</div>
</template>

<script setup>
import { ref, computed } from 'vue';

const { text, maxLength } = defineProps({
	text: {
		type: String,
		required: true,
	},
	maxLength: {
		type: Number,
		default: 100,
	},
});

const isTruncated = ref(true);

const truncatedText = computed(() => {
	if (text.length > maxLength) {
		return text.substring(0, maxLength) + "...";
	} else {
		return text;
	}
});

const toggleText = () => {
	isTruncated.value = !isTruncated.value;
};
</script>
<style scoped>
.view-toggle {
	cursor: pointer;
}

.view-toggle:hover {
	text-decoration: underline;
}
</style>
