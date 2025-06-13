package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfo {
    private Integer companyId;
    private String companyName;
    private String companyIntro;
    private String hrName;        //HR姓名
    private String gender;      //HR性别
    private String email;
    private String phone;
}
