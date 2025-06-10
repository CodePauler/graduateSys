package com.dhu.Service.Impl;

import com.dhu.Mapper.UserMapper;
import com.dhu.Pojo.LoginInfo;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.User;
import com.dhu.Service.UserService;
import com.dhu.Utils.JwtUtils;
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
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userLogin.getId());
        claims.put("username", userLogin.getUsername());
        String jwt = JwtUtils.generateToken(claims);
        if(userLogin!=null){
            return new LoginInfo(userLogin.getId(),userLogin.getUsername(),userLogin.getName(),userLogin.getRole(),jwt);
        }
        else {
            return null; // 登录失败，返回null
        }
    }
}
