package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Pojo.CompanyInfo;
import com.dhu.Pojo.CompanyQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Pojo.StatisticsCompanyJob;
import com.dhu.Service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    // 分页查询
    @RoleCheck({ "admin" })
    @GetMapping
    public Result page(CompanyQueryParam companyQueryParam) {
        log.info("请求查询参数:{}", companyQueryParam);
        PageResult<CompanyInfo> page = companyService.page(companyQueryParam);
        return Result.success(page);
    }

    // ID查询
    @RoleCheck({ "company", "admin" })
    @GetMapping("/{id}")
    public Result getCompanyById(@PathVariable Integer id) {
        log.info("查询公司ID: {}", id);
        CompanyInfo companyInfo = companyService.getCompanyById(id);
        if (companyInfo != null) {
            return Result.success(companyInfo);
        } else {
            return Result.error("公司不存在");
        }
    }

    // 修改信息
    @RoleCheck({ "company", "admin" })
    @PutMapping
    public Result updateCompany(@RequestBody CompanyInfo companyInfo) {
        log.info("修改公司信息: {}", companyInfo);
        companyService.updateByCompanyId(companyInfo);
        return Result.success("修改成功");
    }

    // 删除公司
    @RoleCheck({ "admin" })
    @DeleteMapping
    public Result deleteCompanies(@RequestParam List<Integer> ids) {
        log.info("删除公司ID列表: {}", ids);
        companyService.deleteCompany(ids);
        return Result.success("删除成功");
    }

    // 根据HR用户ID获取公司信息
    @RoleCheck({ "company", "admin" })
    @GetMapping("/getCompanyByHrId/{hrId}")
    public Result getCompanyByHrId(@PathVariable Integer hrId) {
        log.info("根据HR用户ID查询公司信息: {}", hrId);
        CompanyInfo companyInfo = companyService.getCompanyByHrId(hrId);
        if (companyInfo != null) {
            return Result.success(companyInfo);
        } else {
            return Result.error("公司不存在或未分配");
        }
    }

    // 根据HR用户ID获取自己的信息
    @RoleCheck({ "company", "admin" })
    @GetMapping("/self/{hrId}")
    public Result getSelfCompanyInfo(@PathVariable Integer hrId, HttpServletRequest request) {
        log.info("查询HR用户ID: {}", hrId);
        Integer userId = (Integer) request.getAttribute("userId");
        if (!userId.equals(hrId)) {
            return Result.error("只能查询自己的信息");
        }
        CompanyInfo companyInfo = companyService.getCompanyProfileByHrId(hrId);
        if (companyInfo != null) {
            return Result.success(companyInfo);
        } else {
            return Result.error("公司不存在或未分配");
        }
    }

    // 获取企业招聘统计汇总（从company_job_summary视图）
    @RoleCheck({ "company", "admin" })
    @GetMapping("/{companyId}/job-summary")
    public Result getCompanyJobSummary(@PathVariable Integer companyId, HttpServletRequest request) {
        log.info("获取企业招聘统计汇总，公司ID: {}", companyId);

        // 如果是company角色，需要验证只能查看自己的数据
        String role = (String) request.getAttribute("role");
        if ("company".equals(role)) {
            Integer userId = (Integer) request.getAttribute("userId");
            CompanyInfo userCompany = companyService.getCompanyByHrId(userId);
            if (userCompany == null || !userCompany.getCompanyId().equals(companyId)) {
                return Result.error("只能查询自己公司的统计数据");
            }
        }
        try {
            StatisticsCompanyJob summary = companyService.getJobSummaryByCompanyId(companyId);
            if (summary != null) {
                return Result.success(summary);
            } else {
                return Result.error("未找到该公司的统计数据");
            }
        } catch (Exception e) {
            log.error("获取企业招聘统计失败: {}", e.getMessage());
            return Result.error("获取统计数据失败");
        }
    }
}
