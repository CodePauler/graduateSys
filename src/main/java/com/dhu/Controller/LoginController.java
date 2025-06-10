package com.dhu.Controller;

import com.dhu.Pojo.LoginInfo;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 调用用户服务进行注册
        return userService.register(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("登录请求参数：{}", user);
        LoginInfo loginInfo = userService.login(user);
        if (loginInfo != null) {
            return Result.success(loginInfo);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
