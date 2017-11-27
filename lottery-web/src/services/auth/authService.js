import jwtDecode from 'jwt-decode';
import crypto from 'crypto-js';

const JWT = 'jwt';
const CREDENTIALS = 'credentials';
// URL and endpoint constants
const LOGIN = 'auth';

// TODO change to cookie
function getJWT() {
  return window.localStorage.getItem(JWT);
}

export default Vue => ({
  signIn(username, password) {
    return Vue.http.post(LOGIN,
      { username, password: crypto.SHA512(password).toString(crypto.enc.Hex) })
      .then((response) => {
        const token = response.body.token;
        const credentials = jwtDecode(token);

        console.log(token);
        console.log(credentials);

        window.localStorage.setItem(JWT, token);
        window.localStorage.setItem(CREDENTIALS, JSON.stringify(credentials));
        this.credentials = credentials;
        return true;
      }).catch(() => false);
  },
  logout() {
    window.localStorage.removeItem(JWT);
    window.localStorage.removeItem(CREDENTIALS);

    this.credentials = null;
  },
  authHeader() {
    return `Bearer ${getJWT()}`;
  },
  isLogged() {
    return getJWT();
  },
  isUnauthorized(response) {
    return response.status === 401 || response.status === 403;
  },
  getCredentialsData() {
    return this.credentials;
  },
});
