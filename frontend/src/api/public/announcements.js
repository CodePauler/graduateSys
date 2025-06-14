import request from "@/utils/request";

// 条件查询所有公告
export const queryAnnouncementsApi = (params) =>{
    console.log("查询所有公告：params=", params);
    return request.get('/announcements', {params});
}

// 根据ID查询公告
export const queryAnnouncementByIdApi = (id) =>{
    console.log("接收参数：id=", id);
    return request.get(`/announcements/${id}`);
}

// 更新公告信息
export const updateAnnouncementApi = (announcement) =>{
    console.log("更新公告：announcement=", announcement);
    return request.put(`/announcements`, announcement);
}

// 删除公告
export const deleteAnnouncementApi = (ids) =>{
    console.log("删除公告：ids=", ids);
    return request.delete(`/announcements?ids=${ids}`);
}