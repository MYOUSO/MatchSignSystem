import axios from 'axios';
import router from '../router/index'; // 假设你的路由实例在 ./router.js 中

// 添加一个响应拦截器
axios.interceptors.response.use(response => {
  return response;
}, error => {
  // 如果响应状态码为 401（未授权）
  if (error.response && error.response.data === "NOT_LOGIN") {
    // 将用户重定向到登录页面
    router.push('/login');
  }
  return Promise.reject(error);
});

export default axios;
