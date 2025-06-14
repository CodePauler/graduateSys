package com.dhu.Controller;

import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//岗位列表
@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    /**
     * 分页查询岗位
     */
    @GetMapping
    public Result page(JobQueryParam jobQueryParam){
        log.info("分页查询岗位: {}", jobQueryParam);
        PageResult<JobInfo> page = jobService.page(jobQueryParam);
        return Result.success(page);
    }
}
