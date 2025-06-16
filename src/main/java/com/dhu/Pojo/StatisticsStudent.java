package com.dhu.Pojo;

import lombok.Data;

@Data
public class StatisticsStudent {
    private Integer studentId; // 学生ID
    private String studentName; // 学生姓名
    private String employmentStatus; // 就业状态
    private Integer totalApplications; // 岗位申请数量
    private Integer offerCount; // 收到offer数
}
