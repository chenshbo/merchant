package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.Province;

import java.util.List;

public interface ProvinceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);

    List<Province> selectListByParams(Province province);
}