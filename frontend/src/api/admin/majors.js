import request from "@/utils/request";

// 更新专业信息
export const updateMajorApi = (data) =>{
    console.log("更新专业信息: data=", data);
    return request.put("/majors", data);
}

// 删除专业
export const deleteMajorApi = (ids) => {
    console.log("删除专业: ids=", ids);
    return request.delete(`/majors?ids=${id}`);
}

// 新增专业
export const addMajorApi = (data) => {
    console.log("新增专业: data=", data);
    return request.post("/majors", data);
}