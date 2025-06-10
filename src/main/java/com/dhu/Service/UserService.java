package com.dhu.Service;

import com.dhu.Pojo.LoginInfo;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;

public interface UserService {

//    注册用户
    Result register(User user);

//    用户登录
    LoginInfo login(User user);
}
