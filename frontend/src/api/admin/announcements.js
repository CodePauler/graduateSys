import request from "@/utils/request";

// admin--发布公告
export const insertAnnouncementApi = (announcement) => {
    return request.post('/announcements', announcement)
}