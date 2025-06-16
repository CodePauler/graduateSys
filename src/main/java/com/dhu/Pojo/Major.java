package com.dhu.Pojo;

import lombok.Data;

@Data
public class Major {
    private Integer majorId; // 专业ID
    private String majorName; // 专业名称
    private Integer departmentId; // 外键，关联部门ID
    private String departmentName; // 部门名称
}
