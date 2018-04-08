package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.UserFans;

public interface UserFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    UserFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);
}