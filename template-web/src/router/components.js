import LoginView from "@/views/index.vue";
import HomeView from "@/views/home/index.vue";

export const routerData = [
    {
        path: '/',
        name: 'login',
        component: LoginView
    },
    {
        path: '/home',
        name: 'home',
        component: HomeView
    }
]