package com.dhu.Service.Impl;

import com.dhu.Mapper.DepartmentMapper;
import com.dhu.Pojo.Department;
import com.dhu.Pojo.DepartmentQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Service.DepartmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


//    获取所有学院
    @Override
    public PageResult<Department> getAllDepartments(DepartmentQueryParam departmentQueryParam) {
        PageHelper.startPage(departmentQueryParam.getPage(), departmentQueryParam.getPageSize());
        List<Department> departments = departmentMapper.selectAllDepartments(departmentQueryParam);
        Page<Department> page = (Page<Department>) departments;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

//    新增学院
    @Override
    public void addDepartment(Department department) {
        departmentMapper.insertDepartment(department);
    }

    //修改学院
    @Override
    public void updateDepartment(Department department) {
        departmentMapper.updateDepartment(department);
    }

//    删除学院
    @Override
    public void deleteDepartment(List<Integer> ids) {
        departmentMapper.deleteDepartment(ids);
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return departmentMapper.selectById(id);
    }
}
