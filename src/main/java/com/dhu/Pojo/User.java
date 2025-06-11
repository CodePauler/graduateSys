package com.dhu.Pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String gender;

//    选填
    private String email;
    private String phone;
}
