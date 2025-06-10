// src/api.js
import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/', // 后端接口地址
  timeout: 5000
});

// 登录
export const loginApi = (data) => {
  return instance.post('/login', data);
};

// 注册
export const registerApi = (data) => {
  return instance.post('/register', data);
};

export default instance;
