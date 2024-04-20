import {createRouter, createWebHashHistory} from 'vue-router'
import {getToken} from "@/util/TokenUtil";
import {routerData} from "@/router/components";

const routes = routerData

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

// login verify
router.beforeEach((to, from, next) => {
    const token = getToken()
    const isLogin = token !== ''
    if (to.path === '/') {
        isLogin ? next('/home') : next()
    } else {
        isLogin ? next() : next('/')
    }
})
export default router
