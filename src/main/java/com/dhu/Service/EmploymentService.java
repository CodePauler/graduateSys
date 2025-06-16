package com.dhu.Service;

public interface EmploymentService {

//    学生申请岗位
    void applyJob(Integer studentId, Integer jobId);

//    更新申请
    void updateApplication(String status, Integer jobId, Integer studentId);
}
