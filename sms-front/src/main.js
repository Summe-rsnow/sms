import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import {createPinia} from 'pinia';
import 'element-plus/dist/index.css'

const pinia = createPinia()
const app = createApp(App)

app.use(ElementPlus)
app.use(pinia)

app.mount('#app')

