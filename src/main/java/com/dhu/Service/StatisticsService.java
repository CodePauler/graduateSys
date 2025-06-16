package com.dhu.Service;

import com.dhu.Pojo.*;

import java.util.List;

/**
 * 统计服务接口
 */
public interface StatisticsService {

    List<StatisticsCompanyJob> getCompanyJobStatistics();

    List<StatisticsDepartment> getDepartmentEmploymentStatistics();

    StatisticsGlobal getGlobalEmploymentStatistics();

    List<StatisticsStudent> getStudentJobApplicationsStatistics();
}
