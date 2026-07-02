import { defineConfig } from 'vite';

export default defineConfig({
  server: {
    proxy: {
      '/api/v1': {
        target: 'http://ticketrider-dev-alb-1599948438.us-east-1.elb.amazonaws.com/pulse-java-api',
        changeOrigin: true,
        secure: false,
      },
      '/api/external/songs': {
        target: 'https://music-api.albatross0071.workers.dev/api/songs',
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/api\/external\/songs/, '')
      }
    }
  }
});
