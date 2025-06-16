package com.dhu.Service;

import com.dhu.Pojo.Major;
import com.dhu.Pojo.MajorQueryParam;
import com.dhu.Pojo.PageResult;
import com.dhu.Pojo.Result;

import java.util.List;


public interface MajorService {

//    获取所有学院
    PageResult<Major> page(MajorQueryParam majorQueryParam);

//    新增学院
    void addMajor(Major major);

//    修改学院
    void updateMajor(Major major);

//    删除学院
    void deleteMajor(List<Integer> ids);

//    根据专业ID获取专业信息
    Major getMajorById(Integer id);
}
