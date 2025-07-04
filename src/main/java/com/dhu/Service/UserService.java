package com.dhu.Service;

import com.dhu.Pojo.*;

import java.util.List;
import java.util.Map;

public interface UserService {

//    注册用户
    Result register(User user, Map<String, Object>extraParams);

//    用户登录
    LoginInfo login(User user);

    /**
     * 分页查询
     */
    PageResult<User> page(UserQueryParam userQueryParam);


//    根据ID查询用户
    User getUserById(Integer id);

//   修改用户信息
    void updateUser(User user);

//    删除用户
    void deleteUser(List<Integer> ids);

//    个人中心-信息修改
    void updateProfile(ProfileUpdate user);
}
