package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.City;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List<City> selectListByParams(City city);
}