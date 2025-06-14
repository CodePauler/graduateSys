package com.dhu.Service;

import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;

public interface JobService {

//    分页查询
    PageResult<JobInfo> page(JobQueryParam jobQueryParam);
}
