package com.dhu.Service.Impl;

import com.dhu.Mapper.CompanyMapper;
import com.dhu.Mapper.StudentMapper;
import com.dhu.Mapper.UserMapper;
import com.dhu.Pojo.*;
import com.dhu.Service.UserService;
import com.dhu.Utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Result register(User user, Map<String , Object> extraParams) {
        User existingUser = userMapper.findByUsernameAndPassword(user);
        if(existingUser!=null){
            return Result.error("用户名已存在");
        }
        userMapper.insert(user);

        Integer userId=user.getId();
        log.info("注册用户ID: {}", userId);
//        如果需要根据角色进行不同的处理
        if("student".equals(user.getRole())){
            Student student = new Student();
            if (extraParams.get("studentId") != null) {
                student.setStudentId(Integer.valueOf(extraParams.get("studentId").toString()));
            }
            student.setUserId(userId);
            if (extraParams.get("majorId") != null) {
                student.setMajorId(Integer.valueOf(extraParams.get("majorId").toString()));
            }
            student.setGraduationYear((String) extraParams.get("graduationYear"));
            student.setEmploymentStatus("待业"); // 默认
            studentMapper.insert(student);
        } else if("company".equals(user.getRole())){
            Company company = new Company();

            company.setUserId(userId);
            company.setCompanyName((String) extraParams.get("companyName"));
            company.setCompanyIntro((String) extraParams.get("companyIntro"));
            companyMapper.insert(company);
        } else {
            return Result.error("未知角色");
        }

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
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(List<Integer> ids) {
        userMapper.deleteByIds(ids);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public PageResult<User> page(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(), userQueryParam.getPageSize());
        Page<User> p = (Page<User>) userMapper.list(userQueryParam);
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
