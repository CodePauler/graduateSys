package com.dhu.Service;


import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Student;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Pojo.StudentQueryParam;

public interface StudentService {

    /**
     * 分页查询学生
     */
    PageResult<StudentInfo> page(StudentQueryParam studentQueryParam);

    /**
     * 根据学生ID查询学生信息
     */
    StudentInfo getStudentById(Integer studentId);


    /**
     * 更新学生基本信息
     */
    void updateStudent(Student student);


}
