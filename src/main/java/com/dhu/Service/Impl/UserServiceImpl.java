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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Result register(User user, Map<String, Object> extraParams) {
        log.info("注册用户信息: user:{},extraParms:{}", user,extraParams);
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }
        // 判断是否为后台管理创建（extraParams含有adminCreate=true）
        boolean isAdminCreate = extraParams.get("adminCreate") != null
                && Boolean.TRUE.equals(extraParams.get("adminCreate"));
        if ("admin".equals(user.getRole()) && !isAdminCreate) {
            return Result.error("禁止通过注册接口创建管理员账号");
        }
        userMapper.insert(user);
        Integer userId = user.getId();
        log.info("注册用户ID: {}", userId);
        // 如果需要根据角色进行不同的处理
        if ("student".equals(user.getRole())) {
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
            student.setResumeUrl(""); // 简历url默认空字符串
            studentMapper.insert(student);
        } else if ("company".equals(user.getRole())) {
            Company company = new Company();

            company.setUserId(userId);
            company.setCompanyName((String) extraParams.get("companyName"));
            company.setCompanyIntro((String) extraParams.get("companyIntro"));
            companyMapper.insert(company);
        } else if ("admin".equals(user.getRole())) {
            // 管理员不需要额外处理
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
            claims.put("name", userLogin.getName());
            claims.put("role", userLogin.getRole());
            Integer studentId = null;
            Integer companyId = null;
            if ("student".equals(userLogin.getRole())) {
                log.info("学生登录，用户ID: {}", userLogin.getId());
                studentId = studentMapper.findIdByUserId(userLogin.getId());
                claims.put("studentId", studentId);
            } else if ("company".equals(userLogin.getRole())) {
                log.info("公司登录，用户ID: {}", userLogin.getId());
                companyId = companyMapper.findIdByUserId(userLogin.getId());
                claims.put("companyId", companyId);
            }
            String jwt = JwtUtils.generateToken(claims);
            return new LoginInfo(userLogin.getId(), userLogin.getUsername(), userLogin.getName(), userLogin.getRole(),
                    studentId, companyId,
                    jwt);
        } else {
            return null; // 登录失败，返回null
        }
    }

    @Transactional
    @Override
    public void updateProfile(ProfileUpdate user) {
        if(user.getCompanyName()!=null && !"".equals(user.getCompanyName())) companyMapper.updateProfile(user);
        userMapper.updateProfile(user);
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
