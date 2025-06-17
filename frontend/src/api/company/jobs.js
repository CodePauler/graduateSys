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

//  获取申请信息
export const getApplicationsByJobIdApi = (jobId) => {
    console.log("获取申请信息：jobId=", jobId);
    return request.get(`/hires/applications?jobId=${jobId}`);
}

//  获取岗位列表
export const getJobsByCompanyIdApi = (companyId) => {
    console.log("获取岗位列表：companyId=", companyId);
    return request.get(`/hires/jobs?companyId=${companyId}`);
}

// 通过/拒绝学生申请
export const updateApplicationApi = (status,studentId,jobId) => {
    console.log("更新status:",status,"学生申请：studentId=", studentId, ", jobId=", jobId);
    return request.put(`/hires/applications`,{status,studentId, jobId});
}

// 获取企业招聘统计
export const getCompanyRecruitmentStatsApi = (companyId) => {
    console.log("获取企业招聘统计-companyId:", companyId);
    return request.get(`/companies/${companyId}/recruitment-stats`);
}

// 获取企业招聘统计数据（从company_job_summary视图）
export const getCompanyJobSummaryApi = (companyId) => {
    console.log("获取企业招聘统计-companyId:", companyId);
    return request.get(`/companies/${companyId}/job-summary`);
}