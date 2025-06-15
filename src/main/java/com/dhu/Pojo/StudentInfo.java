package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {  //封装用于展示的所有学生信息
    private Long studentId;
    private String name;
    private String gender;
    private String major;
    private String department;
    private Integer graduationYear;
    private String employmentStatus;
    private String jobTitle;    //职位名称
    private String jobType;     //工作类型名
    private String resumeUrl;  //简历地址
}
