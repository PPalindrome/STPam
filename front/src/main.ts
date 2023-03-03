import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js';
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './style.css'

const app = createApp(App)
app.use(router)
app.use(store)
// app.use(codemirror)
app.mount('#app')
app.use(ElementPlus,{
    locale: zhCn,
})

//去除控制台警告
// app.config.silent = true;



