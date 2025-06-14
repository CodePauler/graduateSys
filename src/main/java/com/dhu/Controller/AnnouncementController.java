package com.dhu.Controller;

import com.dhu.Pojo.Announcement;
import com.dhu.Pojo.AnnouncementQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取所有公共-all
     */
    @GetMapping
    public Result getAllAnnouncements(AnnouncementQueryParam announcementQueryParam){
        log.info("分页查询所有公告请求参数: {}", announcementQueryParam);
        PageResult<Announcement> page = announcementService.page(announcementQueryParam);
        return Result.success(page);
    }

    /**
     * ID查询-admin
     */
    @GetMapping("/{id}")
    public Result getAnnouncementById(@PathVariable Integer id){
        log.info("查询公告ID: {}", id);
        Announcement announcement = announcementService.getAnnouncementById(id);
        if(announcement!=null){
            return Result.success(announcement);
        }
        else{
            return Result.error("公告不存在");
        }
    }

    /**
     * 更新公告-admin
     */
    @PutMapping
    public Result updateAnnouncement(@RequestBody Announcement announcement){
        log.info("修改公告信息: {}", announcement);
        announcementService.updateByAnnouncementId(announcement);
        return Result.success("修改成功");
    }

    /**
     * 发布公告--admin
     */
    @PostMapping
    public Result addAnnouncement(@RequestBody Announcement announcement) {
        log.info("发布公告: {}", announcement);
        announcementService.addAnnouncement(announcement);
        return Result.success("公告发布成功");
    }

    /**
     * 删除公告--admin
     */
    @DeleteMapping
    public Result deleteAnnouncements(@RequestParam List<Integer> ids) {
        log.info("删除公告ID列表: {}", ids);
        announcementService.deleteAnnouncement(ids);
        return Result.success("删除成功");
    }
}
