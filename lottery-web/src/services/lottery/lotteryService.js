const LOTTERY = 'lottery';

export default Vue => ({
  search(options) {
    return Vue.http.get(LOTTERY, { params: options })
      .then(response => response.body)
      .catch(error => Promise.reject(error.body));
  },
});
