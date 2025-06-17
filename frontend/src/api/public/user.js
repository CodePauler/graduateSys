import request from "@/utils/request";

// 根据ID查询自己的信息
export const queryUserByIdApi = (id) =>{
  console.log("接收参数：id=", id);
  return request.get(`/users/self/${id}`);
}

// 更新自己的信息
export const updateUserApi = (user) =>{
  console.log("更新用户：user=", user);
  return request.put(`/users/self`, user);
}
