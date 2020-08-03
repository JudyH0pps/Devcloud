import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import VueCookie from 'vue-cookie'
import './plugins/bootstrap-vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(VueCookie)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
