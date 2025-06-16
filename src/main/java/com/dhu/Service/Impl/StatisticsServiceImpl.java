package com.dhu.Service.Impl;

import com.dhu.Mapper.StatisticsMapper;
import com.dhu.Pojo.*;
import com.dhu.Service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

//    获取公司岗位统计信息
    @Override
    public List<StatisticsCompanyJob> getCompanyJobStatistics() {
        return statisticsMapper.getCompanyJobStatistics();
    }

//    获取学院就业统计信息
    @Override
    public List<StatisticsDepartment> getDepartmentEmploymentStatistics() {
        return statisticsMapper.getDepartmentEmploymentStatistics();
    }

    @Override
    public StatisticsGlobal getGlobalEmploymentStatistics() {
        return statisticsMapper.getGlobalEmploymentStatistics();
    }

    @Override
    public List<StatisticsStudent> getStudentJobApplicationsStatistics() {
        return statisticsMapper.getStudentJobApplicationsStatistics();
    }
}
