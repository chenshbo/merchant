package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.dto.ShopBillDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopBill record);

    int insertSelective(ShopBill record);

    ShopBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopBill record);

    int updateByPrimaryKey(ShopBill record);

    ShopBillDto getSaleTotalAmount(@Param("shopId") Long shopId);

    int updateEarliestSortStatus(@Param("shopId") Long shopId);

    List<ShopBillDto> selectBillList(@Param("shopId") Long shopId);

    ShopBill getFirst(@Param("shopId")Long shopId);

    List<ShopBillDto> selectCurrBillList(@Param("shopId")Long shopId);

    List<ShopBillDto> selectFreeBillList(@Param("shopId")Long shopId);

}