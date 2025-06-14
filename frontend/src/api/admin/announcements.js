import request from "@/utils/request";
// admin端公告相关接口
// 发布公告
export const insertAnnouncementApi = (announcement) => {
    return request.post('/announcements', announcement)
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