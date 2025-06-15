package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;

    private Integer announcementId;
    private String title;
    private String creatorName;
    private Instant beginDate; //开始时间
    private Instant endDate; //结束时间
}
