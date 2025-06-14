package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    private Long announcementId;
    private String title; // 公告标题
    private String content; // 公告内容
    private Long creatorId; // 发布者ID
    private String creatorName; // 发布者姓名
    private LocalDateTime createTime; // 发布时间
    private LocalDateTime updateTime; // 更新时间

    private Boolean isPinned; // 是否置顶
    private Integer viewCount; // 浏览量
    private String attachmentName; // 附件名称
    private String attachmentUrl;  //附件在阿里云的存储路径
}
