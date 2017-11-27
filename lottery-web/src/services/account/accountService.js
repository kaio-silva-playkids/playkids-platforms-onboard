const USER = 'account';

export default Vue => ({
  create(user) {
    return Vue.http.post(
      `${USER}`, user)
      .then(response => response.body)
      .catch(error => Promise.reject(error.body));
  },
});
