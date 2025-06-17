package com.dhu.Service.Impl;

import com.dhu.Mapper.AnnouncementMapper;
import com.dhu.Pojo.Announcement;
import com.dhu.Pojo.AnnouncementQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Service.AnnouncementService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public PageResult<Announcement> page(AnnouncementQueryParam announcementQueryParam) {
        PageHelper.startPage(announcementQueryParam.getPage(),announcementQueryParam.getPageSize());
        Page<Announcement> page = (Page<Announcement>) announcementMapper.getAllAnnouncements(announcementQueryParam);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public Announcement getAnnouncementById(Integer announcementId) {
        announcementMapper.updateViewCount(announcementId);
        return announcementMapper.getAnnouncementById(announcementId);
    }

    @Override
    public void updateByAnnouncementId(Announcement announcement) {
        announcement.setUpdateTime(LocalDateTime.now());
        announcementMapper.updateByAnnouncementId(announcement);
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        announcementMapper.addAnnouncement(announcement);
    }

    @Override
    public void deleteAnnouncement(List<Integer> ids) {
        announcementMapper.deleteAnnouncements(ids);
    }
}
