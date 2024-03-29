const ACCOUNT = 'account';

export default Vue => ({
  create(user) {
    return Vue.http.post(
      `${ACCOUNT}`, user)
      .then(response => response.body)
      .catch(error => Promise.reject(error.body));
  },
});
