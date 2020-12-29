import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `http://localhost:8098/api`,
  timeout: 1000
});


export default {
    hello() {
        return AXIOS.get(`/hello`);
    },
    getUser() {
      return AXIOS.get('/account', {withCredentials: true})
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{withCredentials: true});
    }
}


