import AccountService from '../services/account/accountService';

import AuthService from '../services/auth/authService';

import LotteryService from '../services/lottery/lotteryService';

import UserService from '../services/user/userService';

import TicketService from '../services/ticket/ticketService';

export default {
  /* eslint no-param-reassign: ["error", { "props": false }] */
  install(Vue) {
    // account service
    Vue.accountService = AccountService(Vue);
    Vue.prototype.$accountService = Vue.accountService;
    // auth service
    Vue.authService = AuthService(Vue);
    Vue.prototype.$authService = Vue.authService;
    // lottery service
    Vue.lotteryService = LotteryService(Vue);
    Vue.prototype.$lotteryService = Vue.lotteryService;
    // user service
    Vue.userService = UserService(Vue);
    Vue.prototype.$userService = Vue.userService;
    // ticket service
    Vue.ticketService = TicketService(Vue);
    Vue.prototype.$ticketService = Vue.ticketService;
  },
};
