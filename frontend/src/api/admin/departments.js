import request from '@/utils/request';
//  新增学院
export const addDepartmentApi = (data) => {
    console.log("新增学院: data=", data);
    return request.post('/departments', data);
}

// 删除学院
export const deleteDepartmentApi = (ids) => {
    console.log("删除学院: ids=", ids);
    return request.delete(`/departments?ids=${ids}`);
}

// 更新学院信息
export const updateDepartmentApi = (data) => {
    console.log("更新学院: data=", data);
    return request.put(`/departments`, data);
}