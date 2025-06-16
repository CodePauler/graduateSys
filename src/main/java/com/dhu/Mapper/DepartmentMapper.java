package com.dhu.Mapper;

import com.dhu.Pojo.Department;
import com.dhu.Pojo.DepartmentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {

//    获取所有学院
    List<Department> selectAllDepartments(DepartmentQueryParam departmentQueryParam);

//    新增学院
    void insertDepartment(Department department);

//    修改学院
    void updateDepartment(Department department);

//    删除学院
    void deleteDepartment(List<Integer> ids);

//    根据学院ID获取学院信息
    Department selectById(Integer id);
}
