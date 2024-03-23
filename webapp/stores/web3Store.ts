import { defineStore } from 'pinia';
import { ethers } from 'ethers';
import { chooseBlockchain } from '@/constants/chains';
import type { RuntimeConfig } from 'nuxt/schema';

type StoreState = {
	address: string;
	metamaskInstalled: undefined | boolean;
	metamaskAlertViewedOnce: boolean;
	metamaskDysfunction: boolean;
};

export type Provider = ethers.providers.Web3Provider;

export const useWeb3Store = defineStore('web3Store', () => {
	const state: StoreState = {
		address: '',
		metamaskInstalled: undefined,
		metamaskDysfunction: false,
		metamaskAlertViewedOnce: false,
	};
	let provider: undefined | Provider;

	async function login() {
		console.log('TODO login function');
		if (typeof window !== 'undefined' && window.ethereum && window.ethereum.isMetaMask) {
			console.log('MetaMask is installed');
			state.metamaskInstalled = true;
		} else {
			console.log('MetaMask not installed');
			state.metamaskInstalled = false;
		}

		if (!provider) {
			await setProvider();
			await setAddress();

			if (provider) {
				// Set handler if the account changes
				(provider as Provider).on('accountsChanged', () => {
					setAddress();
				});
			}
		}

	}

	async function logout() {
		console.log('TODO logout function');
	}

	async function setProvider() {
		const ethereum = (window as Window)?.ethereum;
		let chain;
		if (ethereum) {
			try {
				chain = chooseBlockchain();
				await ethereum.request!({
					method: 'wallet_switchEthereumChain',
					params: [{ chainId: chain.chainId }],
				});
			} catch (error: any) {
				try {
					await ethereum.request!({
						method: 'wallet_addEthereumChain',
						params: [chain],
					});
				} catch {
					state.metamaskDysfunction = true;
				}
			}
			provider = new ethers.providers.Web3Provider(ethereum);
		}
	}

	async function setAddress() {
		if (!provider) return;
		const config = useRuntimeConfig();
		const accounts = await provider.send(
			config.public.production === 'true'
				? 'eth_requestAccounts'
				: 'eth_accounts',
			[],
		);
		state.address = accounts[0];
	}

	function isConnected(): boolean {
		return provider !== undefined;
	}

	function signer(): undefined | ethers.Signer {
		return provider ? provider.getSigner() : undefined;
	}

	function contract(): ethers.Contract | undefined {
		if (!provider) return undefined;
		const config = useRuntimeConfig();
		return new ethers.Contract(
			config.public.contractAddress,
			abi as ethers.ContractInterface,
			provider.getSigner(),
		);
	}

	return {
		state,
		login,
		logout,
		setProvider,
		setAddress,
		isConnected,
		signer,
		contract,
	};
});

if (import.meta.hot) {
	import.meta.hot.accept(acceptHMRUpdate(useWeb3Store, import.meta.hot))
}