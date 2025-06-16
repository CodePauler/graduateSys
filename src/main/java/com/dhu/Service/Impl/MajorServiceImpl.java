package com.dhu.Service.Impl;

import com.dhu.Annotation.RoleCheck;
import com.dhu.Mapper.MajorMapper;
import com.dhu.Pojo.Major;
import com.dhu.Pojo.MajorQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Service.MajorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    //获取所有专业
    @Override
    public PageResult<Major> page(MajorQueryParam majorQueryParam) {
        PageHelper.startPage(majorQueryParam.getPage(), majorQueryParam.getPageSize());
        Page<Major> page = (Page<Major>) majorMapper.selectAllMajors(majorQueryParam);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

//    新增学院
    @RoleCheck({"admin"})
    @Override
    public void addMajor(Major major) {
        majorMapper.insertMajor(major);
    }


//    修改学院
    @Override
    public void updateMajor(Major major) {
        majorMapper.updateMajor(major);
    }

//    删除学院
    @Override
    public void deleteMajor(List<Integer> ids) {
        majorMapper.deleteMajor(ids);
    }
}
