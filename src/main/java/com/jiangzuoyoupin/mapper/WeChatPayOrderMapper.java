package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.WeChatPayOrder;

import java.util.List;

public interface WeChatPayOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeChatPayOrder record);

    int insertSelective(WeChatPayOrder record);

    WeChatPayOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeChatPayOrder record);

    int updateByPrimaryKey(WeChatPayOrder record);

    int updateByTradeNo(WeChatPayOrder update);

    List<WeChatPayOrder> selectWeChatOrderList(WeChatPayOrder param);
}