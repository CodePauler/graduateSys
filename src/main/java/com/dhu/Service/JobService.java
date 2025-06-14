package com.dhu.Service;

import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;

import java.util.List;

public interface JobService {

//    all-分页查询
    PageResult<JobInfo> page(JobQueryParam jobQueryParam);

//    all-ID查询
    JobInfo getJobById(Integer jobId);

//    company-更新岗位信息
    void updateByJobId(JobInfo jobInfo);

//    company-删除岗位
    void deleteJob(List<Integer> ids);

    void addJob(JobInfo jobInfo);
}
