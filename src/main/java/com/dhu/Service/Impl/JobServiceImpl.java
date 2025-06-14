package com.dhu.Service.Impl;

import com.dhu.Mapper.JobMapper;
import com.dhu.Pojo.JobInfo;
import com.dhu.Pojo.JobQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Service.JobService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;

    @Override
    public PageResult<JobInfo> page(JobQueryParam jobQueryParam) {
        PageHelper.startPage(jobQueryParam.getPage(), jobQueryParam.getPageSize());
        Page<JobInfo> page = (Page<JobInfo>)jobMapper.getAllJobs(jobQueryParam);
        return new PageResult<>(page.getTotal(), page.getResult());
    }
}
