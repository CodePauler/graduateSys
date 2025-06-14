import request from "@/utils/request";
// 公共的company接口

// 根据HR用户ID获取公司信息
export const queryCompanyByHrIdApi = (userId) =>{
    return request.get(`/companies/getCompanyByHrId/${userId}`);
}