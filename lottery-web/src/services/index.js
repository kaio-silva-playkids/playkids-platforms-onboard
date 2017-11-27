import AccountService from '../services/account/accountService';

import AuthService from '../services/auth/authService';

export default {
  /* eslint no-param-reassign: ["error", { "props": false }] */
  install(Vue) {
    // account service
    Vue.accountService = AccountService(Vue);
    Vue.prototype.$accountService = Vue.accountService;
    // auth service
    Vue.authService = AuthService(Vue);
    Vue.prototype.$authService = Vue.authService;
  },
};
