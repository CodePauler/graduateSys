import request from "@/utils/request";

// 申请职位
export const applyJobApi = (studentId, JobsId) => {
    console.log("申请岗位-studentId:", studentId, "JobsId:", JobsId);
    return request.post('/employments',{studentId, JobsId});
}