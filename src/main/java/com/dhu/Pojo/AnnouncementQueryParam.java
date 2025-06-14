package com.dhu.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
