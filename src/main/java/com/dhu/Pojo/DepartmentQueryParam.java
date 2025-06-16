package com.dhu.Pojo;

import lombok.Data;

@Data
public class DepartmentQueryParam {
    private Integer departmentId;
    private String departmentName;

    private Integer page = 1;
    private Integer pageSize = 5;
}
