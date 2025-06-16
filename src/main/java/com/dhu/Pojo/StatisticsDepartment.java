package com.dhu.Pojo;

import lombok.Data;

@Data
public class StatisticsDepartment {
    private Long departmentId; // 部门ID
    private String departmentName; // 部门名称
    private Integer totalStudents; // 总学生数
    private Integer employedStudents; // 就业学生数
    private String employmentRate; //就业率
}
