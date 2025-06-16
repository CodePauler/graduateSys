package com.dhu.Mapper;

import com.dhu.Pojo.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {

//    获取所有学院
    List<Department> selectAllDepartments();

//    新增学院
    void insertDepartment(Department department);

//    修改学院
    void updateDepartment(Department department);

//    删除学院
    void deleteDepartment(List<Integer> ids);
}
