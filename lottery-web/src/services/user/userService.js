const USER = 'user';

export default Vue => ({
  profile() {
    return Vue.http.get(`${USER}/profile`)
      .then(response => response.body)
      .catch(error => Promise.reject(error.body));
  },
});
