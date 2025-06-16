package com.dhu.Controller;

import com.dhu.Pojo.Department;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


//    获取所有学院
    @GetMapping
    public Result getAllDepartments() {
        log.info("获取所有学院");
        return Result.success(departmentService.getAllDepartments());
    }

//    新增学院
    @PostMapping
    public Result addDepartment(@RequestBody Department department) {
        log.info("新增学院: {}", department);
        departmentService.addDepartment(department);
        return Result.success("新增成功");
    }

//    修改学院
    @PutMapping
    public Result updateDepartment(@RequestBody Department department) {
        log.info("修改学院: {}", department);
        departmentService.updateDepartment(department);
        return Result.success("修改成功");
    }

//    删除学院
    @DeleteMapping
    public Result deleteDepartment(@RequestParam List<Integer> ids) {
        log.info("删除学院ID: {}", ids);
        departmentService.deleteDepartment(ids);
        return Result.success("删除成功");
    }
}
