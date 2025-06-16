package com.dhu.Service;

import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;

import java.util.List;

public interface JobService {

//    all-分页查询
    PageResult<JobInfo> page(JobQueryParam jobQueryParam);

//    all-ID查询
    JobInfo getJobByJobId(Integer jobId);

//    company-更新岗位信息
    void updateByJobId(JobInfo jobInfo);

//    company-删除岗位
    void deleteJob(List<Integer> ids);

//    company-发布岗位
    void addJob(JobInfo jobInfo);

//    company-查询自己发布的岗位
    List<JobInfo> getJobsByCompanyId(Integer companyId);

//    student-查询自己申请的岗位
    List<JobInfo> getMyApplicationJob(Integer studentId);
}
