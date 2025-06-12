import request from "@/utils/request";

// 条件查询用户 
export const queryUsersApi = (params) => {
  return request.get('/users', { params });
}