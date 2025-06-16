package com.dhu.Mapper;

import com.dhu.Pojo.Major;
import com.dhu.Pojo.MajorQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {

//    获取所有专业
    List<Major> selectAllMajors(MajorQueryParam majorQueryParam);

//    新增学院
    void insertMajor(Major major);

//    修改学院
    void updateMajor(Major major);

//    删除学院
    void deleteMajor(List<Integer> ids);

//    根据专业ID获取专业信息
    Major selectMajorById(Integer id);
}
