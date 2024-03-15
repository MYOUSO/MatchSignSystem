import Vue from 'vue';
import axios from 'axios';
import qs from 'qs';

const host = 'http://192.168.43.53';
const baseURL = host + ':8080';

axios.defaults.withCredentials = false;
axios.defaults.timeout = 2500;

// 请求拦截
axios.interceptors.request.use(
  config => {
	const user =JSON.parse(window.localStorage.getItem("user")) 
	config.headers.Authoritization = `Bearer ${user.token}`
	return config;
  },
  err => {
    return Promise.reject(err); 
  }
)
// 响应拦截
axios.interceptors.response.use(
	res => {
    return res;
  },
  err => {
    return Promise.reject(err);
  }
)
const fetch = (url, method, data) => {
  data = data ? data : {};

  let httpDefaultOpts = { //http默认配置
    method: method,
    url: baseURL + url,
    params:data,
    data:qs.stringify(data),
    headers: method=='get'?{
      "Accept": "application/json",
      "Content-Type": "application/json; charset=UTF-8"
    }:{
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
  }
 
  if(method =='get'){
    delete httpDefaultOpts.data;
  }else{
    delete httpDefaultOpts.params;
  }
    
  let promise = new Promise(function(resolve, reject) {
    axios(httpDefaultOpts).then(
      res => {
        resolve(res);
      }
    ).catch(
      res => {
        reject(res);
      }
    )
  })
  return promise;
}

const fetchGet = (url, data) => {
	return fetch(url, "get", data);
}
const fetchPost = (url, data) => {
	return fetch(url, "post", data);
}

Vue.prototype.fetchGet = fetchGet;
Vue.prototype.fetchPost = fetchPost;
