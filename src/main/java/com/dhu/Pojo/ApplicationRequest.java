package com.dhu.Pojo;

import lombok.Data;

@Data
public class ApplicationRequest {
    private String status; //申请状态
    private Integer studentId; //学生ID
    private Integer jobId; //岗位ID
}
