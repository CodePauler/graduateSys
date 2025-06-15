import request from "@/utils/request";

// 申请职位
export const applyJobApi = (studentId, jobId) => {
    console.log("申请岗位-studentId:", studentId, "jobId:", jobId);
    return request.post('/employments',{studentId, jobId});
}