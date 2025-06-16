package com.dhu.Controller;

import com.dhu.Pojo.*;
import com.dhu.Service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 统计信息控制器
 */
@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 统计公司招募情况
     */
    @GetMapping("/company")
    public Result getCompanyJobStatistics() {
        log.info("获取公司招募情况统计");
        List<StatisticsCompanyJob> statistics = statisticsService.getCompanyJobStatistics();
        return Result.success(statistics);
    }

    /**
     * 统计学院就业情况
     */
    @GetMapping("/department")
    public Result getDepartmentEmploymentStatistics() {
        log.info("获取学院就业情况统计");
        List<StatisticsDepartment> statisticsDepartments = statisticsService.getDepartmentEmploymentStatistics();
        return Result.success(statisticsDepartments);
    }

    /**
     * 统计学生总体就业情况
     */
    @GetMapping("/global")
    public Result getGlobalEmploymentStatistics() {
        log.info("获取学生总体就业情况统计");
        StatisticsGlobal statisticsGlobals= statisticsService.getGlobalEmploymentStatistics();
        return Result.success(statisticsGlobals);
    }

    /**
     * 每个学生收到的岗位申请数量
     */
    @GetMapping("/student")
    public Result getStudentJobApplicationsStatistics() {
        log.info("获取每个学生收到的岗位申请数量统计");
        List<StatisticsStudent> statisticsStudents = statisticsService.getStudentJobApplicationsStatistics();
        return Result.success(statisticsStudents);
    }
}
