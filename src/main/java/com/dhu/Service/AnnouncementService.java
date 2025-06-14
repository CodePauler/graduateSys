package com.dhu.Service;

import com.dhu.Pojo.Announcement;
import com.dhu.Pojo.AnnouncementQueryParam;
import com.dhu.Pojo.PageResult;

import java.util.List;

public interface AnnouncementService {
    PageResult<Announcement> page(AnnouncementQueryParam announcementQueryParam);

    Announcement getAnnouncementById(Integer id);

    void updateByAnnouncementId(Announcement announcement);

    void addAnnouncement(Announcement announcement);

    void deleteAnnouncement(List<Integer> ids);
}
