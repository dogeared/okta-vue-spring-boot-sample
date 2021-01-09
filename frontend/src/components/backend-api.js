import axios from 'axios'
import appConfig from '@/.app.config'

const AXIOS = axios.create({
  baseURL: appConfig.backendUrl + '/api'
});


export default {
    hello() {
        return AXIOS.get(`/hello`);
    },
    getUser() {
      return AXIOS.get('/account', {withCredentials: true})
    },
    getSecured() {
        return AXIOS.get(`/secured`,{withCredentials: true});
    }
}


