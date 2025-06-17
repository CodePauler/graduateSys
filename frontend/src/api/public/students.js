import request from "@/utils/request";

export const queryStudentByUserIdApi = (id) => {
  console.log("接收参数：id=", id);
  return request.get(`/students/self/${id}`);
}