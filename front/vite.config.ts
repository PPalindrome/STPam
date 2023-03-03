import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
const targetUrl='http://localhost:8081'

// https://vitejs.dev/config/
export default defineConfig({
    base:'./',
    plugins: [
        // ...
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
        vue()
    ],
    server: {
        host: "0.0.0.0",
        port: 8070,
        open: true,
        proxy:{
            '/login': {
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/login': '/login'
                }
            },
            '/register': {
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/register': '/register'
                }
            },
            '/setting/userSetting':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/setting/userSetting': '/setting/userSetting'
                }
            },
            '/files/delete/model':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/files/delete/model': '/files/delete/model'
                }
            },
            '/build':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/build': '/build'
                }
            },
            '/download':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/download': '/download'
                }
            },
            '/data/setting':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/data/setting': '/data/setting'
                }
            },
            '/hdfs/put':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/hdfs/put': '/hdfs/put'
                }
            },
            '/hdfs/file':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/hdfs/file': '/hdfs/file'
                }
            },
            '/hdfs/download':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/hdfs/download': '/hdfs/download'
                }
            },
            '/encrypt':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/encrypt': '/encrypt'
                }
            },
            '/save':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/save': '/save'
                }
            },
            '/queryModels':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/queryModels': '/queryModels'
                }
            },
            '/queryDatasets':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/queryDatasets': '/queryDatasets'
                }
            },
            '/getRole':{
                target:targetUrl,
                changeOrigin: true,
                cookiePathRewrite: {
                    '^/getRole': '/getRole'
                }
            },
            '/directoryStructure':{
                target:targetUrl,
                changeOrigin:true,
                cookiePathRewrite:{
                    '^/directoryStructure':'/directoryStructure'
                }
            },
            '/fileEdit/getCode':{
                target:targetUrl,
                changeOrigin:true,
                cookiePathRewrite:{
                    '^/fileEdit/getCode':'/fileEdit/getCode'
                }
            },
            '/fileEdit/saveCode':{
                target:targetUrl,
                changeOrigin:true,
                cookiePathRewrite:{
                    '^/fileEdit/saveCode':'/fileEdit/saveCode'
                }
            }
        }
    },
})

