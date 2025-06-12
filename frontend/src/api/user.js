import request from "@/utils/request";

// 条件查询用户 
export const queryUsersApi = (params) => {
  return request.get('/users', { params });
}

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

// 删除用户
export const deleteUserApi = (ids) =>{
  console.log("删除用户：ids=", ids);
  return request.delete(`/users?ids=${ids}`);
}