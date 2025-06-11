package com.dhu.Service;

import com.dhu.Pojo.*;

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
}
