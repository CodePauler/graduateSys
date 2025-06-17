package com.dhu.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmploymentMapper {

//    插入就业信息
    void insert(Integer studentId, Integer jobId);

//    通过岗位ID和学生ID批准申请
    boolean updateApplication(String status,Integer jobId, Integer studentId);

//    通过岗位ID和学生ID删除申请
    void deleteApplication(Integer jobId, Integer studentId);

//    检查岗位是否已满
    Boolean isJobFull(Integer jobId);
}
