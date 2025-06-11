package com.dhu.Service.Impl;

import com.dhu.Mapper.UserMapper;
import com.dhu.Pojo.*;
import com.dhu.Service.UserService;
import com.dhu.Utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(User user) {
        User existingUser = userMapper.findByUsernameAndPassword(user);
        if(existingUser!=null){
            return Result.error("用户名已存在");
        }
        if(user.getRole()==null){
            user.setRole("student"); // 默认角色为学生
        }
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public LoginInfo login(User user) {
        User userLogin = userMapper.findByUsernameAndPassword(user);
        if (userLogin != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", userLogin.getId());
            claims.put("username", userLogin.getUsername());
            String jwt = JwtUtils.generateToken(claims);
            return new LoginInfo(userLogin.getId(), userLogin.getUsername(), userLogin.getName(), userLogin.getRole(),
                    jwt);
        } else {
            return null; // 登录失败，返回null
        }
    }

    @Override
    public PageResult<User> page(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(), userQueryParam.getPageSize());
        Page<User> p = (Page<User>) userMapper.list(userQueryParam);
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
