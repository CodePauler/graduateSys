package com.dhu.Service.Impl;

import com.dhu.Mapper.CompanyMapper;
import com.dhu.Pojo.CompanyInfo;
import com.dhu.Pojo.CompanyQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.StatisticsCompanyJob;
import com.dhu.Service.CompanyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    // 条件查询
    @Override
    public PageResult<CompanyInfo> page(CompanyQueryParam companyQueryParam) {
        // 设置分页参数
        PageHelper.startPage(companyQueryParam.getPage(), companyQueryParam.getPageSize());
        // 执行查询
        Page<CompanyInfo> p = (Page<CompanyInfo>) companyMapper.list(companyQueryParam);
        // 返回分页结果
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    // ID查询
    @Override
    public CompanyInfo getCompanyById(Integer companyId) {
        return companyMapper.getById(companyId);
    }

    // 更新信息
    @Override
    public void updateByCompanyId(CompanyInfo companyInfo) {
        companyMapper.updateByCompanyId(companyInfo);
    }

    // 批量删除
    @Override
    public void deleteCompany(List<Integer> ids) {
        companyMapper.deleteByIds(ids);
    }

    @Override
    public CompanyInfo getCompanyByHrId(Integer hrId) {
        // 通过HR ID查询公司信息
        return companyMapper.getByHrId(hrId);
    }

    @Override
    public CompanyInfo getCompanyProfileByHrId(Integer hrId) {
        return companyMapper.selectCompanyProfileByHrId(hrId);
    }

    @Override
    public StatisticsCompanyJob getJobSummaryByCompanyId(Integer companyId) {
        return companyMapper.getJobSummaryByCompanyId(companyId);
    }
}
