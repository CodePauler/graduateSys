package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorQueryParam {
    private Integer majorId; // 专业ID
    private String majorName; // 专业名称
    private Integer departmentId; // 外键，关联部门ID
    private String departmentName; // 部门名称
    private Integer page = 1; // 当前页码
    private Integer pageSize = 10; // 每页显示数量
}
