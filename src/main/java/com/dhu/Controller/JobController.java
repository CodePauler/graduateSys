package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//岗位列表
@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    /**
     * all-分页查询岗位
     */
    @GetMapping
    public Result page(JobQueryParam jobQueryParam){
        log.info("分页查询岗位: {}", jobQueryParam);
        PageResult<JobInfo> page = jobService.page(jobQueryParam);
        return Result.success(page);
    }

    /**
     * all-ID查询
     */
    @GetMapping("/{id}")
    public Result getPageByJobId(@PathVariable Integer id){
        log.info("查询岗位ID: {}", id);
        JobInfo jobInfo = jobService.getJobByJobId(id);
        if (jobInfo != null) {
            return Result.success(jobInfo);
        } else {
            return Result.error("岗位不存在");
        }
    }

    /**
     * company-修改岗位信息
     */
    @RoleCheck({"company","admin"})
    @PutMapping
    public Result updateJob(@RequestBody JobInfo jobInfo) {
        log.info("修改岗位信息: {}", jobInfo);
        jobService.updateByJobId(jobInfo);
        return Result.success("修改成功");
    }

    /**
     * company-删除岗位
     */
    @RoleCheck({"company","admin"})
    @DeleteMapping()
    public Result deleteJobs(@RequestParam List<Integer> ids) {
        log.info("删除岗位ID: {}", ids);
        jobService.deleteJob(ids);
        return Result.success("删除成功");
    }

    /**
     * company-发布岗位
     */
    @RoleCheck({"company","admin"})
    @PostMapping
    public Result addJob(@RequestBody JobInfo jobInfo) {
        log.info("发布岗位: {}", jobInfo);
        jobService.addJob(jobInfo);
        return Result.success("岗位发布成功");
    }

}
