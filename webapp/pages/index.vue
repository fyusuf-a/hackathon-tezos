<template>
	<div>
		<h1>Welcome to the homepage</h1>
		<TestComponent>
			This is an auto-imported component
		</TestComponent>
		<h1>{{ testStore.counter }}</h1>
		<button @click="testStore.increment">Increment</button>
		<button @click="testStore.decrement">Decrement</button>
		<button @click="web3Store.login">Login</button>
	</div>
</template>
<script setup lang="ts">
const testStore = useTestStore();
const web3Store = useWeb3Store();
web3Store.state;
</script>