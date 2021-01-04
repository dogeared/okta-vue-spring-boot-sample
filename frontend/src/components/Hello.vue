<template xmlns="http://www.w3.org/1999/html">
  <div class="hello">
    <img src="./../assets/spring-boot-vuejs-logo.png">
    <h1>{{ hellomsg }}</h1>
    <h2>See the sources here: </h2>
    <ul>
      <li>
        <a href="github.com/okta-samples/okta-vue-spring-boot-sample" target="_blank">
          github.com/okta-samples/okta-vue-spring-boot-sample</a>
      </li>
    </ul>
    <div v-if="$store.getters.getUser">
      <h3>User Info for {{ $store.getters.getUser.given_name }}:</h3>
      <div style="text-align: center;">
      <textarea>
        {{ JSON.stringify($store.getters.getUser, undefined, 4) }}
      </textarea>
      </div>
    </div>
  </div>
</template>

<script>

import store from '../store'
import router from '../router'

export default {
  name: 'hello',
  props: { hellomsg: { type: String, required: true } },
  mounted() {
    store.dispatch("getUser")
      .then(() => {
        if (sessionStorage.getItem('requested-url')) {
          let loginDestination = sessionStorage.getItem('requested-url');
          sessionStorage.removeItem('requested-url');
          router.push(loginDestination);
        }
      })
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

textarea {
  width: 50%;
  min-height: 30rem;
  font-family: "Lucida Console", Monaco, monospace;
  font-size: 0.8rem;
  line-height: 1.2;
  border: none;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
</style>
