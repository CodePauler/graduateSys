import request from "@/utils/request";

// 登录
export const loginApi = (data) => {
  return instance.post('/login', data);
};

// 注册
export const registerApi = (data) => {
  return instance.post('/register', data);
};

export default instance;