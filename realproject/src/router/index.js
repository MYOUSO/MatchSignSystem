import Vue from 'vue'
import VueRouter from 'vue-router'
/* import axios from 'axios'; */

Vue.use(VueRouter)

const routes = [
  {
    path: '/games',
    name: 'games',
    component: () => import('../views/commppetion/GamesView.vue')
  },
  {
    path: '/account',
    name: 'account',
    component: () => import('../views/commppetion/AccountHome.vue')
  },
  {
    path: '/administrators-addition',
    name: 'administrators-addition',
    component: () => import('../views/commppetion/AdminationGames.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/commppetion/AccountLogin.vue')
  }, 
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/commppetion/AccountRegister.vue')
  },{
    path: '/info',
    name: 'information',
    component: () => import('../views/commppetion/AccountInfo.vue')
  },{
    path: '/gameInfo',
    name: 'gameInfo',
    component: () => import('../views/commppetion/GameInfo.vue')
  },
  {
    path:'/',
    redirect: '/login'
  }
];
/* router.beforeEach((to, from, next) => {
  // 在路由跳转前检查用户是否登录
  axios.get('checkLogin',
  {params:{
    token:JSON.parse(window.localStorage.getItem("user")).token,
  }

    
  })
    .then(response => {
      // 如果后端返回 "NOT_LOGIN"，强制跳转到登录页面
      if (response.data.data === "NOT_LOGIN") {
        next('/login');
      } else {
        // 如果已登录，继续导航
        next();
      }
    })
    .catch(error => {
      // 处理请求失败的情况
      console.error('Check login failed:', error);
      next();  // 如果请求失败，继续导航，可以根据实际需求修改
    });
} 
);
*/

const router = new VueRouter({
  routes
})

export default router
