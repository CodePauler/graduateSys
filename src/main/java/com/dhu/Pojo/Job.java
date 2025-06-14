package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private Integer jobId; // 岗位ID
    private String title; // 岗位名称
    private Integer typeId; // 岗位类型ID（外键，关联岗位类型表）
    private Integer demandNumber; // 招聘人数
    private String hiredNumber; // 已录用人数
    private Integer companyId; // 外键，关联公司ID
    private String status; // 岗位状态（待审核/已通过/不通过）
    private String description; // 岗位描述
}
