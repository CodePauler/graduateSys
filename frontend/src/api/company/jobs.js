import request from "@/utils/request";
// company端jobs相关接口
// 发布岗位
export const insertJobApi = (job) => {
    console.log("发布岗位：job=", job);
    return request.post('/jobs', job);
}

// 更新岗位信息
export const updateJobApi = (job) => {
    console.log("更新岗位：job=", job);
    return request.put(`/jobs`, job);
}

// 删除岗位
export const deleteJobApi = (ids) => {
    console.log("删除岗位：ids=", ids);
    return request.delete(`/jobs?ids=${ids}`);
}