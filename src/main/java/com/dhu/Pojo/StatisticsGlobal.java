package com.dhu.Pojo;

import lombok.Data;

@Data
public class StatisticsGlobal {
    private Integer totalStudents; // 总学生数
    private Integer employedStudents; // 已就业学生数
    private Integer unemployedStudents; // 未就业学生数
    private Integer companyCount; // 公司数量
    private Integer totalJobDemand; // 总需求人数
    private Integer totalHired; // 总招聘人数
    private Integer jobCount; // 岗位数量
    private Integer totalApplications; // 总申请数量
    private Integer announcementsCount; // 公告数量
}
