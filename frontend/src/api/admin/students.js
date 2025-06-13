import request from "@/utils/request";

// 条件查询学生
export const queryStudentsApi = (params) =>{
    console.log("查询学生：params=", params);
    return request.get('/students', { params });
}

//  根据ID查询学生
export const queryStudentByIdApi = (id) =>{
    console.log("接收参数：id=", id);
    return request.get(`/students/${id}`);
}

// 更新学生信息
export const updateStudentApi = (student) =>{
    console.log("更新学生：student=", student);
    return request.put(`/students`, student);
}

// 删除学生
export const deleteStudentApi = (ids) =>{
    console.log("删除学生：ids=", ids);
    return request.delete(`/students?ids=${ids}`);
}