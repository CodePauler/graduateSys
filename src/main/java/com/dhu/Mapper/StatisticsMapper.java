package com.dhu.Mapper;

import com.dhu.Pojo.StatisticsCompanyJob;
import com.dhu.Pojo.StatisticsDepartment;
import com.dhu.Pojo.StatisticsGlobal;
import com.dhu.Pojo.StatisticsStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
//    获取公司岗位统计信息
    List<StatisticsCompanyJob> getCompanyJobStatistics();

//    获取学院就业统计信息
    List<StatisticsDepartment> getDepartmentEmploymentStatistics();

//    获取学生总体就业统计信息
    StatisticsGlobal getGlobalEmploymentStatistics();

//    获取每个学生收到的岗位申请数量
    List<StatisticsStudent> getStudentJobApplicationsStatistics();
}
