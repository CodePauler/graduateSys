package com.dhu.Service;

import com.dhu.Pojo.StudentInfo;

import java.util.List;

public interface EmploymentService {

//    学生申请岗位
    void applyJob(Integer studentId, Integer jobId);

//    更新申请
    void updateApplication(String status, Integer jobId, Integer studentId);
}
