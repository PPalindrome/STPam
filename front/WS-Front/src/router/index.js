import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home.vue'),
        children: [
            {
                path: '/wpsServiceAdd',
                name: 'wpsServiceAdd',
                meta: {title: 'WPS算法服务添加'},
                component: () => import('../components/WPSServiceAdd.vue'),
                // children: [{
                //         path: '/dataAnalysis/view',
                //         name: 'view',
                //         meta: {title: '数据查看'},
                //         component: () => import('../components/stData/dataView.vue')
                //     },]
            },
            {
                path: '/wpsServiceUse',
                name: 'wpsServiceUse',
                meta: {title: 'WPS算法服务使用'},
                component: () => import('../components/WPSServiceUse.vue'),
            },
            {
                path: '/wpsServiceManage',
                name: 'wpsServiceManage',
                meta: {title: 'WPS算法服务管理'},
                component: () => import('../components/WPSServiceManage.vue'),
            },
        ],
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
