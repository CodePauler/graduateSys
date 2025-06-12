package com.dhu.Mapper;

import com.dhu.Pojo.User;
import com.dhu.Pojo.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

//    注册用户
    void insert(User user);

//    根据用户名查询用户
    User findByUsernameAndPassword(User user);

//  查询所有用户
    List<User> list(UserQueryParam userQueryParam);

    User getById(Integer id);

    void updateById(User user);
}
