package com.dhu.Pojo;

import lombok.Data;

// 封装公司岗位统计信息
@Data
public class StatisticsCompanyJob {
    private String companyName; // 公司名称
    private Integer jobCount; // 岗位总数
    private Integer demandCount;
    private Integer applicationCount;
    private Integer approvedCount;
}
