package com.dhu.Mapper;

import com.dhu.Pojo.Announcement;
import com.dhu.Pojo.AnnouncementQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnouncementMapper {

//    分页查询公告-all
    List<Announcement> getAllAnnouncements(AnnouncementQueryParam announcementQueryParam);

//    ID查询-admin
    Announcement getAnnouncementById(Integer announcementId);

//    更新公告-admin
    void updateByAnnouncementId(Announcement announcement);

//    发布公告-admin
    void addAnnouncement(Announcement announcement);

//    批量删除公告-admin
    void deleteAnnouncements(List<Integer> ids);

//    更新浏览量
    void updateViewCount(Integer announcementId);

//
}
