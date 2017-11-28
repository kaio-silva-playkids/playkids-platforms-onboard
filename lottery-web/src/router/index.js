import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/Home.vue';
import Login from '../components/Login.vue';
import Account from '../components/Account.vue';

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/account',
      name: 'account',
      component: Account,
    },
    {
      path: '*',
      redirect: '/',
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (to.path !== '/login' && !Vue.authService.isLogged()) {
    if (to.path === '/account') {
      next();
    } else {
      next({
        path: '/login',
      });
    }
  } else if (to.path === '/logout') {
    Vue.authService.logout();
    next();
  } else {
    next();
  }
});

export default router;
