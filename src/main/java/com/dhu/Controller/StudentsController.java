package com.dhu.Controller;

import com.dhu.Pojo.*;
import com.dhu.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
