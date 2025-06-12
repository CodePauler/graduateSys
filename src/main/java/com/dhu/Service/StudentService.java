package com.dhu.Service;


import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.StudentInfo;
import com.dhu.Pojo.StudentQueryParam;

public interface StudentService {

    /**
     * 分页查询学生
     */
    PageResult<StudentInfo> page(StudentQueryParam studentQueryParam);
}
