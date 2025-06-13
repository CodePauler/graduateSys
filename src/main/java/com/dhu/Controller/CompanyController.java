package com.dhu.Controller;

import com.dhu.Pojo.CompanyInfo;
import com.dhu.Pojo.CompanyQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.CompanyService;
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

//    分页查询
    @GetMapping
    public Result page(CompanyQueryParam companyQueryParam){
        log.info("请求查询参数:{}", companyQueryParam);
        PageResult<CompanyInfo> page = companyService.page(companyQueryParam);
        return Result.success(page);
    }

//    ID查询
    @GetMapping("/{id}")
    public Result getCompanyById(@PathVariable Integer id){
        log.info("查询公司ID: {}", id);
        CompanyInfo companyInfo = companyService.getCompanyById(id);
        if (companyInfo != null) {
            return Result.success(companyInfo);
        } else {
            return Result.error("公司不存在");
        }
    }

//    修改信息
    @PutMapping
    public Result updateCompany(@RequestBody CompanyInfo companyInfo) {
        log.info("修改公司信息: {}", companyInfo);
        companyService.updateByCompanyId(companyInfo);
        return Result.success("修改成功");
    }

//    删除公司
    @DeleteMapping
    public Result deleteCompanies(@RequestParam List<Integer> ids) {
        log.info("删除公司ID列表: {}", ids);
        companyService.deleteCompany(ids);
        return Result.success("删除成功");
    }

}
