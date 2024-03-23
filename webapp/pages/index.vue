<template>
	<div>
		<h1>Welcome to the homepage</h1>
		<TestComponent>
			This is an auto-imported component
		</TestComponent>
		<h1>{{ testStore.counter }}</h1>
		<button @click="testStore.increment">Increment</button>
		<button @click="testStore.decrement">Decrement</button>
                
                <div style="display:flex; flex-direction:column; margin-top: 20px;">
                <input v-model="email" type="email" placeholder="Email" />
                <button v-if="magicStore.isConnected" @click="() => magicStore.logout()">Logout</button>
                <button v-else @click="() => magicStore.loginWithEmail(email)">Login</button>
                {{ magicStore.isConnected ? 'Connected' : 'Not connected' }}
                </div>
	</div>
</template>

<script setup lang="ts">
const testStore = useTestStore();
const magicStore = useMagicStore();
const email = ref('');
</script>