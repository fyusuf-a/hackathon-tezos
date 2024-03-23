export function chooseBlockchain(production: string) {
	return production === 'true'
		? {
			chainId: '0x61',
			rpcUrls: ['https://data-seed-prebsc-1-s1.binance.org:8545'],
			chainName: 'Binance Smart Chain Testnet',
			nativeCurrency: {
				name: 'tBNB',
				symbol: 'tBNB',
				decimals: 18,
			},
			blockExplorerUrls: ['https://testnet.bscscan.com'],
		}
		: {
			chainId: '0x539',
			rpcUrls: ['http://localhost:8545'],
			chainName: 'ganache',
		};
	/*
		return {
			chainId: 128123,
			rpcUrls: ['https://node.ghostnet.etherlink.com'],
			chainName: 'Etherlink Testnet',
			nativeCurrency: {
				name: 'XTZ',
				symbol: 'XTZ',
				decimals: 18,
			},
			blockExplorerUrls: ['https://testnet-explorer.etherlink.com/'],
		}
	*/
}