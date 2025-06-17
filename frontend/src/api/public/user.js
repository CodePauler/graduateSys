import request from "@/utils/request";

// 根据ID查询用户
export const queryUserByIdApi = (id) =>{
  console.log("接收参数：id=", id);
  return request.get(`/users/${id}`);
}

// 更新用户信息
export const updateUserApi = (user) =>{
  console.log("更新用户：user=", user);
  return request.put(`/users`, user);
}