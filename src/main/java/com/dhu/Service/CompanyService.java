package com.dhu.Service;

import com.dhu.Pojo.*;

import java.util.List;

public interface CompanyService {
    /**
     * 分页查询公司
     */
    PageResult<CompanyInfo> page(CompanyQueryParam companyQueryParam);

    /**
     * 根据公司ID查询公司信息
     */
    CompanyInfo getCompanyById(Integer companyId);


    /**
     * 更新公司基本信息
     */
    void updateByCompanyId(CompanyInfo companyInfo);


    /**
     * 批量删除公司
     */
    void deleteCompany(List<Integer> ids);

    /**
     * 根据HR用户ID获取公司信息
     */
    CompanyInfo getCompanyByHrId(Integer hrId);

    /**
     * 获取公司简介信息
     */
    CompanyInfo getCompanyProfileByHrId(Integer hrId);
}
