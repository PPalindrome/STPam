import { createApp } from 'vue'
import './style.css'

// element-plus框架
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 引入路由配置
import router from './router/index'

// 引入axios配置
import axios from './utils/request'

import App from './App.vue'

const app = createApp(App)

// app.prototype.axios = axios;

app.use(ElementPlus, {
    locale: zhCn,
})
app.use(router)
// app.use(axios)
app.mount('#app')
