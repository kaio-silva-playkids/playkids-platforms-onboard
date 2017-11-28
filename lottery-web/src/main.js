// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import VueResource from 'vue-resource';

import lodash from 'lodash';
import moment from 'moment';

import Element from './element';

import App from './App.vue';
import router from './router';
import Services from './services';
import Filters from './components/filters';

Vue.lodash = lodash;
Vue.moment = moment;

Vue.prototype.$lodash = lodash;

Vue.config.productionTip = false;

Vue.use(Element);

Vue.use(VueResource);
Vue.use(Services);
Vue.use(Filters);

Vue.http.options.root = 'http://localhost:8082/api';
Vue.http.interceptors.push((request, next) => {
  const isLooged = Vue.authService.isLogged();
  if (isLooged) {
    request.headers.set('Authorization', Vue.authService.authHeader());
  }

  next();
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App },
});
