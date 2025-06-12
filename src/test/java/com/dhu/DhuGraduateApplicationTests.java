package com.dhu;

import com.dhu.Mapper.StudentMapper;
import com.dhu.Pojo.Student;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Pojo.StudentQueryParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.lang.Long.valueOf;

@SpringBootTest
class DhuGraduateApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testStudentMapper(){
        StudentQueryParam studentQueryParam;
        studentQueryParam=new StudentQueryParam();
        studentQueryParam.setStudentId(valueOf(1));
        List<StudentInfo> list = studentMapper.list(studentQueryParam);
        for (StudentInfo studentInfo : list) {
            System.out.println(studentInfo);
        }

    }
}
