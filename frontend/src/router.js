import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import Bootstrap from '@/components/Bootstrap'
import Protected from '@/components/Protected'

import store from './store'

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: Hello },
        { path: '/callservice', component: Service },
        { path: '/bootstrap', component: Bootstrap },
        {
            path: '/protected',
            component: Protected,
            meta: {
                requiresAuth: true
            }
        },

        // otherwise redirect to home
        { path: '*', redirect: '/' }
    ]
});

router.beforeEach((to, from, next) => {

    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            sessionStorage.setItem('requested-url', to.fullPath);
            let loc = window.location;
            const port = loc.port ? ':' + loc.port : '';
            //loc.href = `//${loc.hostname}${port}/oauth2/authorization/okta`;
            loc.href = `http://localhost:8098/oauth2/authorization/okta`;
        } else {
            next();
        }
    } else {
        next(); // make sure to always call next()!
    }
});

export default router;