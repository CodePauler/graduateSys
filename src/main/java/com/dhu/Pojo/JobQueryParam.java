package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobQueryParam {
    private Integer page=1;
    private Integer pageSize=10;

    private Integer jobId; // 岗位ID
    private String title; // 岗位名称
    private String jobType; // 岗位类型名称
    private String typeId; // 岗位类型ID
    private String companyName; // 公司名称
    private String description; // 岗位描述
    private String status; // 岗位状态（待审核/已通过/不通过）
    private String salary; // 岗位薪资
    private String location; // 岗位类型名称
    private Integer studentId; // 学生ID，用于查询学生已申请的岗位
    private String hireStatus;
}
