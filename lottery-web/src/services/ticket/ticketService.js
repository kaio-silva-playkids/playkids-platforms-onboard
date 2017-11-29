const TICKET = 'ticket';

export default Vue => ({
  buy(lottery) {
    return Vue.http.post(`${TICKET}/buy`, lottery)
      .then(response => response.body)
      .catch(error => Promise.reject(error.body));
  },
});
