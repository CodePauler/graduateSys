package com.dhu.Mapper;

import com.dhu.Pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {

    void insert(Company company);

    // 分页查询学生
    List<CompanyInfo> list(CompanyQueryParam companyQueryParam);

    // 根据ID查询企业信息
    CompanyInfo getById(Integer companyId);

    // 更新企业信息
    void updateByCompanyId(CompanyInfo companyInfo);

    // 根据id批量删除企业
    void deleteByIds(List<Integer> ids);

    CompanyInfo getByHrId(Integer hrId);

    Integer findIdByUserId(Integer id);

    // 根据HR ID查询公司profile
    CompanyInfo selectCompanyProfileByHrId(Integer hrId);

    // 个人中心-信息修改
    void updateProfile(ProfileUpdate user);

    // 根据公司ID获取招聘统计汇总
    StatisticsCompanyJob getJobSummaryByCompanyId(Integer companyId);

//    根据公司ID列表获取用户ID列表
    List<Integer> getUserIdsByCompanyIds(List<Integer> companyIds);
}
