package com.dhu.Mapper;

import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobMapper {
//    分页查询
    List<JobInfo> getAllJobs(JobQueryParam jobQueryParam);

//    ID查询
    JobInfo getJobByJobId(Integer jobId);

//    admin--更新岗位信息
    void updateJobById(JobInfo jobInfo);

//    admin--发布岗位
    void addJob(JobInfo jobInfo);

//    admin--批量删除岗位
    void deleteJobs(List<Integer> ids);

//  company--查询自己发布的岗位
    List<JobInfo> getJobsByCompanyId(Integer companyId);

//    student--查询自己申请的岗位
    List<JobInfo> getMyApplicationJob(Integer studentId);
}
