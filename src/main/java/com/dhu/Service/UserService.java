package com.dhu.Service;

import com.dhu.Pojo.*;

public interface UserService {

//    注册用户
    Result register(User user);

//    用户登录
    LoginInfo login(User user);

    /**
     * 分页查询
     */
    PageResult<User> page(UserQueryParam userQueryParam);
}
