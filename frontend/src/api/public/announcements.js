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