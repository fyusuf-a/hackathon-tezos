import { defineStore } from 'pinia';

export const useTestStore = defineStore('testStore', () => {
	const counter = ref(0);

	function increment() {
		counter.value++;
	}

	function decrement() {
		counter.value--;
	}

	return {
		counter,
		increment,
		decrement,
	};
});

if (import.meta.hot) {
	import.meta.hot.accept(acceptHMRUpdate(useTestStore, import.meta.hot))
}