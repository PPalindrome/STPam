import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({

  plugins: [vue()],

  server: {
    host: "0.0.0.0",
    port:8066,
    open:true,
    proxy: {
      '/queryWPSModels': {
        target: 'http://localhost:8088',
        changeOrigin: true,
        cookiePathRewrite: {
          '^/queryWPSModels': '/queryWPSModels'
        }
      },

    }
  },
})
