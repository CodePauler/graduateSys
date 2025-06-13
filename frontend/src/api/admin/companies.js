import request from "@/utils/request";

// 条件查询企业
export const queryCompaniesApi = (params) =>{
    console.log("查询企业：params=", params);
    return request.get('/companies', {params});
}

// 根据ID查询企业
export const queryCompanyByIdApi = (id) =>{
    console.log("接收参数：id=", id);
    return request.get(`/companies/${id}`);
}

// 更新企业信息
export const updateCompanyApi = (company) =>{
    console.log("更新企业：company=", company);
    return request.put(`/companies`, company);
}

// 删除企业
export const deleteCompanyApi = (ids) =>{
    console.log("删除企业：ids=", ids);
    return request.delete(`/companies?ids=${ids}`);
}