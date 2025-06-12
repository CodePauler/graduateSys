import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// import vueJsx from '@vitejs/plugin-vue-jsx'
// import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server:{
    proxy:{
      '/api': {
        target: 'http://localhost:8080', // 后端服务的地址
        changeOrigin: true, // 是否改变源
        rewrite: (path) => path.replace(/^\/api/, ''), // 重写路径，将/api前缀去掉
      }
    }
  }
})