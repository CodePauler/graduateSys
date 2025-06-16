package com.dhu.Controller;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Mapper.MajorMapper;
import com.dhu.Pojo.Major;
import com.dhu.Pojo.MajorQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;
import com.dhu.Service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/majors")
public class MajorController {

    @Autowired
    private MajorService majorService;

//    查询所有专业
    @GetMapping
    public Result getAllMajors(MajorQueryParam majorQueryParam) {
        log.info("获取所有专业,查询参数：{}", majorQueryParam);
        PageResult<Major> page = majorService.page(majorQueryParam);
        return Result.success(page);
    }

//    新增专业
    @RoleCheck({"admin"})
    @PostMapping
    public Result addMajor(@RequestBody Major major) {
        log.info("新增专业: {}", major);
        if (major.getMajorName() == null || major.getMajorName().isEmpty()) {
            return Result.error("专业名称不能为空");
        }
        return Result.success("新增成功");
    }

//    修改专业信息
    @RoleCheck({"admin"})
    @PutMapping
    public Result updateMajor(@RequestBody Major major) {
        log.info("修改专业信息: {}", major);
        if (major.getMajorId() == null) {
            return Result.error("专业ID不能为空");
        }
        majorService.updateMajor(major);
        return Result.success("修改成功");
    }

//    删除专业
    @RoleCheck({"admin"})
    @DeleteMapping
    public Result deleteMajor(@RequestParam List<Integer> ids) {
        log.info("删除专业ID: {}", ids);
        if (ids == null) {
            return Result.error("专业ID不能为空");
        }
        majorService.deleteMajor(ids);
        return Result.success("删除成功");
    }
}
