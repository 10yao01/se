// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import '@/styles/index.scss' // global css
import '@/icons' // icon
import VueParticles from 'vue-particles'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Vuex)
Vue.use(VueParticles) 
/* eslint-disable no-new */


new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
