package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.UserShopowner;

public interface UserShopownerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserShopowner record);

    int insertSelective(UserShopowner record);

    UserShopowner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserShopowner record);

    int updateByPrimaryKey(UserShopowner record);
}