package com.dhu.Pojo;

import lombok.Data;

// 封装公司岗位统计信息
@Data
public class StatisticsCompanyJob {
    private Integer companyId; // 公司ID
    private String companyName; // 公司名称
    private Integer jobCount; // 发布岗位数
    private Integer demandCount; // 总需求人数
    private Integer approvedCount; // 通过人数
    private Integer applicationCount; // 申请数量
}
