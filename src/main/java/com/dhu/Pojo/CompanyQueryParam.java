package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;

    private Integer companyId;
    private String companyName;
    private String hrName;
    private String gender;
    private String email;
    private String phone;
}
