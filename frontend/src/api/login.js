import request from "@/utils/request";

// 登录
export const loginApi = (data) => {
  console.log("登录请求数据：", data);
  return request.post('/login', data);
};

// 注册
export const registerApi = (data, adminCreate = false) => {
  const params = adminCreate ? { ...data, adminCreate: true } : data;
  return request.post('/register', params);
};
