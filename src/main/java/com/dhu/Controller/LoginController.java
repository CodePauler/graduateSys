package com.dhu.Controller;

import com.dhu.Pojo.LoginInfo;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * 把通用属性封装到User对象，其他属性传递给Service层根据role决定封装到Student/Company
     * 
     * @param params
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> params) {
        // 调用用户服务进行注册
        log.info("注册请求参数：{}", params);
        User user = new User();
        user.setUsername((String) params.get("username"));
        user.setPassword((String) params.get("password"));
        user.setRole((String) params.get("role"));
        user.setName((String) params.get("name"));
        user.setGender((String) params.get("gender"));
        user.setEmail((String) params.get("email"));
        user.setPhone((String) params.get("phone"));
        return userService.register(user, params);
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
