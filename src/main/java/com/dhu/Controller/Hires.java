package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Service.JobService;
import com.dhu.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * company-我的招聘
 */
@Slf4j
@RestController
@RequestMapping("/hires")
public class Hires {

    @Autowired
    JobService jobService;

    @Autowired
    private StudentService studentService;

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
        List<StudentInfo> applications = studentService.getApplicationStudentsByJobId(jobId);
        return Result.success(applications);
    }
}
