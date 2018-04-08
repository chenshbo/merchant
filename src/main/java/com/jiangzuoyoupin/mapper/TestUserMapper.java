package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.TestUser;
import org.apache.ibatis.annotations.Param;

public interface TestUserMapper {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}