package com.dhu.Controller;

import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Pojo.UserQueryParam;
import com.dhu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
