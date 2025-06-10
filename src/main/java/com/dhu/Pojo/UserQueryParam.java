package com.dhu.Pojo;

import lombok.Data;

@Data
public class UserQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String username;
    private String name;
    private String role;
    private String gender;
}
