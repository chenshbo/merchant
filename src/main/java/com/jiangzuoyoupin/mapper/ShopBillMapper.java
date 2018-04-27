package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.ShopBill;

public interface ShopBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopBill record);

    int insertSelective(ShopBill record);

    ShopBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopBill record);

    int updateByPrimaryKey(ShopBill record);
}