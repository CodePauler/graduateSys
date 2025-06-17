package com.dhu.Pojo;

import lombok.Data;

@Data
public class ProfileUpdate {
    private Integer userId;
    private String username; // hr用户名
    private String password;
    private String phone; // hr手机号
    private String email; // hr邮箱
    private Integer companyId; // 公司ID
    private String companyName; // 公司名称
    private String companyIntro; // 公司简介
}
