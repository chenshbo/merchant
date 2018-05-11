package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.WeChatUser;

public interface WeChatUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeChatUser record);

    int insertSelective(WeChatUser record);

    WeChatUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeChatUser record);

    int updateByPrimaryKey(WeChatUser record);

    WeChatUser getByParams(WeChatUser wechat);

    int updateMobileNo(WeChatUser params);

    int updateBalance(WeChatUser userUpdate);
}