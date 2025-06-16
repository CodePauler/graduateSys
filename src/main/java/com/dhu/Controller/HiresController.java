package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.*;
import com.dhu.Service.EmploymentService;
import com.dhu.Service.JobService;
import com.dhu.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * company-我的招聘
 */
@Slf4j
@RestController
@RequestMapping("/hires")
public class HiresController {

    @Autowired
    JobService jobService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private EmploymentService employmentService;

    /**
     * company-查看自己发布的招聘
     */
    @RoleCheck({"company","admin"})
    @GetMapping("/jobs")
    public Result getJobs(@RequestParam Integer companyId){
        log.info("查询公司ID: {} 发布的岗位", companyId);
        if (companyId == null) {
            return Result.error("公司ID不能为空");
        }
        List<JobInfo> pageResult = jobService.getJobsByCompanyId(companyId);
        return Result.success(pageResult);
    }

    /**
     * company-根据岗位Id查询收到的申请（学生信息）
     */
    @RoleCheck({"company","admin"})
    @GetMapping("/applications")
    public Result getApplicationStudentsByJobId(@RequestParam Integer jobId) {
        log.info("查询岗位ID: {} 的申请信息", jobId);
        if (jobId == null) {
            return Result.error("岗位ID不能为空");
        }
        List<ApplicationStudent> applications = studentService.getApplicationStudentsByJobId(jobId);
        return Result.success(applications);
    }

    /**
     * 通过学生申请
     */
    @RoleCheck({"company","admin"})
    @PostMapping("/applications")
    public Result updateApplication(@RequestBody ApplicationRequest applicationRequest) {
        String status = applicationRequest.getStatus();
        Integer jobId = applicationRequest.getJobId();
        Integer studentId = applicationRequest.getStudentId();
        log.info("{}学生ID: {} 的申请，岗位ID: {}",status, studentId, jobId);
        if (jobId == null || studentId == null) {
            return Result.error("岗位ID和学生ID不能为空");
        }
        employmentService.updateApplication(status,jobId, studentId);
        return Result.success("申请处理成功");
    }
}
