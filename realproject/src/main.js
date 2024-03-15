import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
/*导入axios*/
/* import axios from "axios"; */
import axios from 'axios';
/*全局绑定axios*/
Vue.prototype.$axios = axios;
/*导入qs*/
import qs from 'qs';
/*全局绑定*/
Vue.prototype.$qs = qs;
/*引入mock数据*/
require('../src/views/mock/sy.js')


export const eventBus = new Vue();
Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
