package com.dhu.Mapper;

import com.dhu.Pojo.Student;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
//    注册学生
    void insert(Student student);

//   分页查询学生
    List<StudentInfo> list(StudentQueryParam studentQueryParam);

//    根据ID查询学生信息
    StudentInfo getById(Integer studentId);

//    更新学生信息
    void updateByStudentId(Student student);

//    根据id批量删除学生
    void deleteByIds(List<Integer> ids);

    Integer findIdByUserId(Integer id);
}
