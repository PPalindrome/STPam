import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js';
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import './style.css'
//导入i18n
import {createI18n} from 'vue-i18n'
//导入语言包
import zh from './lang/zh'
import en from './lang/en'
//消息
//必须是messages而不能是message
const messages={
    zh,
    en
}
//创建i18n对象
const i18n=createI18n({
    globalInjection: true, //全局生效$t
    messages,
    //指定语言
    locale:'zh',
    //指定切换的内容
    legacy: false,
})


createApp(App).use(router).use(store).use(i18n).use(ElementPlus).mount('#app')


//去除控制台警告
// app.config.silent = true;



