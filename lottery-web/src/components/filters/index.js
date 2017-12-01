import moment from 'moment';

export default {
  /* eslint no-param-reassign: ["error", { "props": false }] */
  install(Vue) {
    Vue.filter('formatDate', (value) => {
      if (value) {
        return moment(value).format('DD/MM/YYYY HH:mm:ss');
      }
      return null;
    });
  },
};
