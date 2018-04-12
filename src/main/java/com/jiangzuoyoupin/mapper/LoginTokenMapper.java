package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.LoginToken;

public interface LoginTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginToken record);

    int insertSelective(LoginToken record);

    LoginToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginToken record);

    int updateByPrimaryKey(LoginToken record);
}