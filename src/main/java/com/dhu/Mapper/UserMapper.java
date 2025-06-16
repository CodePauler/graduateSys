package com.dhu.Mapper;

import com.dhu.Pojo.User;
import com.dhu.Pojo.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // 注册用户
    void insert(User user);

    // 根据用户名查询用户 用于登录
    User findByUsernameAndPassword(User user);

    // 根据用户名查重
    User findByUsername(String username);

    // 查询所有用户
    List<User> list(UserQueryParam userQueryParam);

    // ID查询
    User getById(Integer id);

    // 修改信息
    void updateById(User user);

    // 批量删除用户
    void deleteByIds(List<Integer> ids);
}
