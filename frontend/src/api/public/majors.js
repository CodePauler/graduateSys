import request from "@/utils/request";

// 获取专业列表
export const queryMajorApi = (params) => {
    console.log("条件查询所有专业:params=", params);
    return request.get("/majors",{params})
}

// 根据专业ID获取专业信息
export const queryMajorByIdApi = (id) => {
    return request.get(`/majors/${id}`)
}