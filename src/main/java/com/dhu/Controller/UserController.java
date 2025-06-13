package com.dhu.Controller;

import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Pojo.UserQueryParam;
import com.dhu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result page(UserQueryParam userQueryParam){
        log.info("请求查询参数:{}",userQueryParam);
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
    public Result updateUser(@RequestBody User user){
        log.info("修改用户信息: {}", user);
        userService.updateUser(user);
        return Result.success("修改成功");
    }

//    删除用户（支持批量删除）
    @DeleteMapping
    public Result deleteUser(@RequestParam List<Integer> ids){
        log.info("删除用户ID列表: {}", ids);
        userService.deleteUser(ids);
        return Result.success("删除成功");
    }
}
