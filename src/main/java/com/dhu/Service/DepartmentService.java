package com.dhu.Service;

import com.dhu.Pojo.Department;
import com.dhu.Pojo.DepartmentQueryParam;
import com.dhu.Pojo.PageResult;

import java.util.List;

public interface DepartmentService {

//    获取所有部门
    PageResult<Department> getAllDepartments(DepartmentQueryParam departmentQueryParam);

//    新增学院
    void addDepartment(Department department);

//    修改学院
    void updateDepartment(Department department);

//    删除学院
    void deleteDepartment(List<Integer> ids);

//    根据学院ID获取学院信息
    Department getDepartmentById(Integer id);
}
