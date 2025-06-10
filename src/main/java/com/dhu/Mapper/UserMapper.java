package com.dhu.Mapper;

import com.dhu.Pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

//    注册用户
    void insert(User user);

//    根据用户名查询用户
    User findByUsernameAndPassword(User user);
}
