import request from "@/utils/request";
// all-jobs相关接口
// 条件查询所有岗位
export const queryJobsApi = (params) => {
    console.log("查询所有岗位：params=", params);
    return request.get('/jobs', { params });
}

// 根据ID查询岗位
export const queryJobByIdApi = (id) => {
    console.log("接收参数：id=", id);
    return request.get(`/jobs/${id}`);
}

// 查询所有职业类型
export const queryJobTypesApi = () => {
    console.log("查询所有职业类型");
    return request.get('/jobs/types');
}
