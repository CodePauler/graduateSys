package com.dhu.Controller;

import com.dhu.Pojo.Result;
import com.dhu.Service.EmploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 就业信息
 */
@Slf4j
@RestController
@RequestMapping("/employments")
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;

//    学生申请岗位
    @PostMapping
    public Result allpyJob(@RequestBody Map<String, Integer> params) {
        Integer studentId = params.get("studentId");
        Integer jobId = params.get("jobId");
        log.info("学生ID: {}, 申请岗位ID: {}", studentId, jobId);
        employmentService.applyJob(studentId, jobId);
        return Result.success("申请成功");
    }

}
