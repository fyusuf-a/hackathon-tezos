import { createVuetify } from 'vuetify'
import '@mdi/font/css/materialdesignicons.css'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

export default defineNuxtPlugin(nuxtApp => {
	const vuetify = createVuetify({
		components,
		directives,
        ssr: false,
        theme: {
            defaultTheme: "light",
            themes: {
                light: {
                    colors: {
                        primary: "#8b34fa",
                    }
				}
            }
        },
	})
	nuxtApp.vueApp.use(vuetify)
})