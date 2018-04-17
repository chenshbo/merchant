package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.UserSupplier;
import com.jiangzuoyoupin.dto.UserSupplierDto;
import com.jiangzuoyoupin.req.SupplierQueryReq;

import java.util.List;

public interface UserSupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSupplier record);

    int insertSelective(UserSupplier record);

    UserSupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSupplier record);

    int updateByPrimaryKey(UserSupplier record);

    List<UserSupplierDto> selectByParams(SupplierQueryReq params);

    int updateByParams(UserSupplier userSupplier);
}