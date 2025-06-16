package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.Department;
import com.dhu.Pojo.DepartmentQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


//    获取所有学院
    @GetMapping
    public Result getAllDepartments(DepartmentQueryParam departmentQueryParam) {
        log.info("获取所有学院，查询参数：{}", departmentQueryParam);
        PageResult<Department> departments = departmentService.getAllDepartments(departmentQueryParam);
        return Result.success(departments);
    }

//    新增学院
    @RoleCheck({"admin"})
    @PostMapping
    public Result addDepartment(@RequestBody Department department) {
        log.info("新增学院: {}", department);
        departmentService.addDepartment(department);
        return Result.success("新增成功");
    }

//    根据学院ID获取学院信息
    @GetMapping("/{id}")
    public Result getDepartmentById(@PathVariable Integer id) {
        log.info("查询学院ID: {}", id);
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return Result.success(department);
        } else {
            return Result.error("学院不存在");
        }
    }

//    修改学院
    @RoleCheck({"admin"})
    @PutMapping
    public Result updateDepartment(@RequestBody Department department) {
        log.info("修改学院: {}", department);
        departmentService.updateDepartment(department);
        return Result.success("修改成功");
    }

//    删除学院
    @RoleCheck({"admin"})
    @DeleteMapping
    public Result deleteDepartment(@RequestParam List<Integer> ids) {
        log.info("删除学院ID: {}", ids);
        departmentService.deleteDepartment(ids);
        return Result.success("删除成功");
    }
}
