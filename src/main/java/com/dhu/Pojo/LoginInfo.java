package com.dhu.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String role;
    private Integer studentId; // 学生ID，可为空
    private Integer companyId; // 公司ID，可为空
    private String token; // JWT令牌
}
