package com.dhu.Mapper;

import com.dhu.Pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
//    注册学生
    void insert(Student student);
}
