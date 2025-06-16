package com.dhu.Service;

public interface EmploymentService {

//    学生申请岗位
    void applyJob(Integer studentId, Integer jobId);

//    更新申请
    void updateApplication(String status, Integer jobId, Integer studentId);

//    学生查看自己申请的岗位
    void refuseOffer(Integer jobId, Integer studentId);
}
