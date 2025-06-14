package com.dhu.Pojo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo {
    private Integer jobId; // 岗位ID
    private String title; // 岗位名称
    private String jobType; // 岗位类型名称
    private Integer typeId; // 岗位类型ID
    private String description; // 岗位描述
    private Integer demandNumber; // 招聘人数
    private String hiredNumber; // 已录用人数
    private Integer companyId; // 外键，关联公司ID
    private String companyName; // 公司名称
    private String companyIntro; // 公司简介
    private String status; // 岗位状态（待审核/已通过/不通过）
}
