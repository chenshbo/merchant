package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.WechatUser;

public interface WechatUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);
}