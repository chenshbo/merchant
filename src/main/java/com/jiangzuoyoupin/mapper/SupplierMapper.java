package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.Supplier;
import com.jiangzuoyoupin.dto.SupplierDto;
import com.jiangzuoyoupin.req.SupplierQueryReq;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    List<SupplierDto> selectByParams(SupplierQueryReq params);

    int updateByParams(Supplier Supplier);
}