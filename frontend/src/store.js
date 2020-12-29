import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        loginSuccess: false,
        loginError: false,
        user: undefined
    },
    mutations: {
        login_success(state, payload){
            state.loginSuccess = true;
            state.user = payload.user;
        },
        login_error(state, payload){
            state.loginError = true;
            state.user = undefined;
        }
    },
    actions: {
        getUser({commit}) {
            return new Promise((resolve, reject) => {
                api.getUser()
                    .then(response => {
                        console.log("Response: '" + response.data + "' with Statuscode " + response.status);
                        if(response.status == 200) {
                            console.log("Login successful");
                            // place the loginSuccess state into our vuex store
                            commit('login_success', {user: response.data});
                        }
                        resolve(response)
                    })
                    .catch(error => {
                        console.log("Error: " + error);
                        // place the loginError state into our vuex store
                        commit('login_error', {});
                        reject("Invalid credentials!")
                    })
            })
        }
    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUser: state => state.user
    }
})