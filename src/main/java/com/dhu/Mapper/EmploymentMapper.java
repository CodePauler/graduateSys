package com.dhu.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmploymentMapper {

//    插入就业信息
    void insert(Integer studentId, Integer jobId);
}
