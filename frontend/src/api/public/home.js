import request from "@/utils/request";

export const getDepartmentInfoApi = () => {
  return request.get('/home');
}