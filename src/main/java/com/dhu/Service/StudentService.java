package com.dhu.Service;


import com.dhu.Pojo.*;

import java.util.List;

public interface StudentService {

    /**
     * 分页查询学生
     */
    PageResult<StudentInfo> page(StudentQueryParam studentQueryParam);

    /**
     * 根据学生ID查询学生信息
     */
    StudentInfo getStudentById(Integer studentId);


    /**
     * 更新学生基本信息
     */
    void updateByStudentId(Student student);


    /**
     * 批量删除学生
     */
    void deleteStudent(List<Integer> ids);

    String getResumeById(Integer id);

    /**
     * 保存简历
     */
    void updateResumeById(Integer studentId, String resumeUrl);

    /**
     * 根据岗位ID查询申请该岗位的学生信息
     */
    List<ApplicationStudent> getApplicationStudentsByJobId(Integer jobId);

}
