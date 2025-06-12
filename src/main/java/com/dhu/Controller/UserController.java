package com.dhu.Controller;

import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Pojo.UserQueryParam;
import com.dhu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result page(UserQueryParam userQueryParam){
        log.info("请求查询参数:",userQueryParam);
        PageResult<User> page = userService.page(userQueryParam);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        log.info("查询用户ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }

//    修改用户
    @PutMapping
    public Result insertUser(@RequestBody User user){
        log.info("修改用户信息: {}", user);
        userService.updateUser(user);
        return Result.success();
    }
}
