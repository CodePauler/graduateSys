package com.dhu.Pojo;


import lombok.Data;

// 企业看到的学生申请信息
@Data
public class ApplicationStudent {
    private Integer studentId;
    private String name;
    private String gender;
    private String major;
    private String graduationYear;
    private String phone;
    private String status;
    private String resumeUrl;
}
