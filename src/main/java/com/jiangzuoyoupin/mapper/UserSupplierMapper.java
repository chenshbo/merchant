package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.UserSupplier;

public interface UserSupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSupplier record);

    int insertSelective(UserSupplier record);

    UserSupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSupplier record);

    int updateByPrimaryKey(UserSupplier record);
}