package com.dhu.Controller;

import com.dhu.Pojo.*;
import com.dhu.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    private StudentService studentService;

//    分页查询
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("请求查询参数:{}",studentQueryParam);
        PageResult<StudentInfo> page = studentService.page(studentQueryParam);
        return Result.success(page);
    }


//    根据ID查询
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

//    更新学生信息
    @PutMapping
    public Result updateStudent(@RequestBody Student student) {
        log.info("修改学生信息: {}", student);
        studentService.updateByStudentId(student);
        return Result.success("修改成功");
    }

//    删除学生
    @DeleteMapping
    public Result deleteStudents(@RequestParam List<Integer>ids) {
        log.info("删除学生ID列表: {}", ids);
        studentService.deleteStudent(ids);
        return Result.success("删除成功");
    }

}
