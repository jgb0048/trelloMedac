import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [tailwindcss()],
  server: { port: 3000 } // 👈 correrá en http://localhost:3000
})
