import './assets/main.css'

import { createApp } from 'vue';
import { Quasar, Notify } from 'quasar';
import { createPinia } from 'pinia';
import '@quasar/extras/material-icons/material-icons.css';
import 'quasar/dist/quasar.css';

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia();

app.use(pinia)
app.use(router);
app.use(Quasar, {
  plugins: {
    Notify
  }
})

app.mount('#app')
