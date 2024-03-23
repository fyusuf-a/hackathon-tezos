// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
	ssr: false,
	devtools: { enabled: true },
	css: ['vuetify/lib/styles/main.sass'],
	build: {
		transpile: ['vuetify'],
	},
	modules: [
		'@vueuse/nuxt',
		'@pinia/nuxt',
	],
	pinia: {
		storesDirs: ['./stores/**'],
	},
    vite: {
        server: {
            proxy: {
                '/api/': {
                    target: 'http://localhost:8080/',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''),
                },
                '/ghostnet/': {
                    target: 'https://node.ghostnet.etherlink.com/',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/ghostnet/, ''),
					headers: {
						"Content-Type": "application/json",
						"Host": "node.ghostnet.etherlink.com",
					}
                }
            }
        },
	}
})
