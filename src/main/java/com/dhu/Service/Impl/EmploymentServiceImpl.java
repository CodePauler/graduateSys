package com.dhu.Service.Impl;

import com.dhu.Mapper.EmploymentMapper;
import com.dhu.Service.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmploymentServiceImpl implements EmploymentService {

    @Autowired
    private EmploymentMapper employmentMapper;

    @Override
    public void applyJob(Integer studentId, Integer jobId) {
        employmentMapper.insert(studentId, jobId);
    }

    @Override
    public void updateApplication(String status,Integer jobId, Integer studentId) {
        employmentMapper.updateApplication(status,jobId, studentId);
    }

    @Override
    public void refuseOffer(Integer jobId, Integer studentId) {
        employmentMapper.deleteApplication(jobId, studentId);
    }
}
