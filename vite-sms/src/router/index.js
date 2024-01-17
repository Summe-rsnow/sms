import {createRouter, createWebHistory} from 'vue-router';
import {useUserStore} from "../stores/index.js";

const routerHistory = createWebHistory();

const routes = [
    {
        path: '/',
        component: () => import('../views/Login.vue')
    }
]

const router = new createRouter({
    history: routerHistory,
    routes
});

router.beforeEach((to, from) => {
    const userStore = useUserStore();
    if (userStore.token) {
        return true;
    } else {
        if (to.path === '/') {
            return true;
        } else {
            return {path: '/'};
        }
    }
});
export default router;