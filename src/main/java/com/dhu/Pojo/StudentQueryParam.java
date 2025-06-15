package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;

    private Integer studentId;
    private String name;
    private String gender;
    private Long majorId;
    private Long departmentId;
    private Integer graduationYear;
    private String employmentStatus;
}