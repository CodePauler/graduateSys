package com.dhu.Controller;

import com.dhu.Pojo.*;
import com.dhu.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("请求查询参数:{}",studentQueryParam);
        PageResult<StudentInfo> page = studentService.page(studentQueryParam);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        log.info("查询学生ID: {}", id);
        StudentInfo studentInfo = studentService.getStudentById(id);
        if (studentInfo != null) {
            return Result.success(studentInfo);
        } else {
            return Result.error("学生不存在");
        }
    }

}
