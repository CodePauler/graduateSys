import request from "@/utils/request";
// 公共的company接口

// 根据HR用户ID获取公司信息
export const queryCompanyByHrIdApi = (userId) =>{
    return request.get(`/companies/getCompanyByHrId/${userId}`);
}

// 根据userId查询公司自己的信息
export const queryCompanyProfileApi = (userId) => {
    console.log("接收参数：userId=", userId);
    return request.get(`/companies/self/${userId}`);
}

// 根据userId更新公司自己的信息
export const updateCompanyProfileApi = (company) => {
    console.log("更新公司：company=", company);
    return request.put(`/companies/self`, company);
}