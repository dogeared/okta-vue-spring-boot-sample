import Vue from 'vue'
import VueHead from 'vue-head'
import appConfig from '@/.app.config'

Vue.config.productionTip = false
Vue.use(VueHead);

new Vue({
  el: '#app',
  template: '<div id="app"></div>',
  head: {
    meta: [
      { "http-equiv": "refresh", "content": "0; URL='" + appConfig.spaUrl + "'" }
    ]    
  }
});