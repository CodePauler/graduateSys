import request from "@/utils/request";
// 获取用户信息

export const queryUsersApi = (params) => {
  return request.get('/users', { params });
}