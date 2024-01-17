import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import {createPinia} from "pinia";
import router from './router/index'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
const pinia = createPinia()

app.use(router)
app.use(pinia)
app.use(ElementPlus)

app.mount('#app')

