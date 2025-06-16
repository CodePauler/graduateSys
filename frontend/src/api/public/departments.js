import request from "@/utils/request";

// 获取所有学院
export const queryDepartmentApi = (params) => {
    console.log("查询所有学院:params=", params);
    return request.get('/departments',{params});
}

//  根据ID查询学院
export const queryDepartmentByIdApi = (id) => {
    console.log("查询学院: id=", id);
    return request.get(`/departments/${id}`);
}
