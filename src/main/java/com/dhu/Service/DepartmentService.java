package com.dhu.Service;

import com.dhu.Pojo.Department;
import com.dhu.Pojo.PageResult;

import java.util.List;

public interface DepartmentService {

//    获取所有部门
    List<Department> getAllDepartments();

//    新增学院
    void addDepartment(Department department);

//    修改学院
    void updateDepartment(Department department);

//    删除学院
    void deleteDepartment(List<Integer> ids);
}
