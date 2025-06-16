import request from "@/utils/request";
// 统计相关接口

// 企业招聘
export const queryCompanyStatisticsApi = () => {
    console.log("查询企业统计信息");
    return request.get('/statistics/company');
}


// 学院就业
export const queryDepartmentStatisticsApi = () => {
    console.log("查询学院统计信息");
    return request.get('/statistics/department');
}

// 全局就业
export const queryGlobalStatisticsApi = () => {
    console.log("查询全局统计信息");
    return request.get('/statistics/global');
}

// 学生就业
export const queryStudentStatisticsApi = () => {
    console.log("查询学生统计信息");
    return request.get('/statistics/student');
}
