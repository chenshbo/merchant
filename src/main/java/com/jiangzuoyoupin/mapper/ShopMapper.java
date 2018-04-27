package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    int updateByWeChatUserId(Shop record);

    Shop selectByWeChatUserId(Long weChatUserId);
}