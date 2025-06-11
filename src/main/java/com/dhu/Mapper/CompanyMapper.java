package com.dhu.Mapper;

import com.dhu.Pojo.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {

    void insert (Company company);
}
