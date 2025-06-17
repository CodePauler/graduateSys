import request from "@/utils/request";

// 申请职位
export const applyJobApi = (studentId, jobId) => {
    console.log("申请岗位-studentId:", studentId, "jobId:", jobId);
    return request.post('/employments',{studentId, jobId});
}

// 查询自己申请的职位
export const queryMyApplicationsApi = (studentId) => {
    console.log("查询自己申请的职位-studentId:", studentId);
    return request.get(`/students/applications?studentId=${studentId}`);
}

// 拒绝offer
export const deleteEmploymentApi = (jobId,studentId) =>{
    console.log("拒绝offer-jobId:", jobId, "studentId:", studentId);
    return request.delete(`/employments?jobId=${jobId}&studentId=${studentId}`)
}

// 获取学生申请统计
export const getStudentApplicationStatsApi = (studentId) => {
    console.log("获取学生申请统计-studentId:", studentId);
    return request.get(`/students/${studentId}/application-stats`);
}