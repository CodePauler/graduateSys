package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.*;
import com.dhu.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RoleCheck({"admin"})
    @GetMapping
    public Result page(UserQueryParam userQueryParam){
        log.info("请求查询参数:{}",userQueryParam);
        PageResult<User> page = userService.page(userQueryParam);
        return Result.success(page);
    }

    @RoleCheck({"admin"})
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
    @RoleCheck({"admin"})
    @PutMapping
    public Result updateUser(@RequestBody User user){
        log.info("修改用户信息: {}", user);
        userService.updateUser(user);
        return Result.success("修改成功");
    }

//    删除用户（支持批量删除）
    @RoleCheck({"admin"})
    @DeleteMapping
    public Result deleteUsers(@RequestParam List<Integer> ids){
        log.info("删除用户ID列表: {}", ids);
        userService.deleteUser(ids);
        return Result.success("删除成功");
    }


//    个人中心-用户自己查询信息
    @GetMapping("/self/{id}")
    public Result getSelfUserInfo(@PathVariable Integer id, HttpServletRequest request) {
        log.info("查询用户ID: {}", id);
        Integer userId = (Integer) request.getAttribute("userId");
        if (!userId.equals(id)) {
            return Result.error("只能查询自己的信息");
        } else {
            return Result.success(userService.getUserById(id));
        }
    }

//    个人中心-用户自己更新信息
    @PutMapping("/self")
    public Result updateSelfUserInfo(@RequestBody ProfileUpdate user, HttpServletRequest request) {
        log.info("修改用户信息: {}", user);
        Integer userId = (Integer) request.getAttribute("userId");
        if (!userId.equals(user.getUserId())) {
            return Result.error("只能修改自己的信息");
        }
        userService.updateProfile(user);
        return Result.success("修改成功");
    }
}
