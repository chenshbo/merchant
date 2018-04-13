package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.LoginToken;
import com.jiangzuoyoupin.domain.WeChatUser;

public interface LoginTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginToken record);

    int insertSelective(LoginToken record);

    LoginToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginToken record);

    int updateByPrimaryKey(LoginToken record);

    LoginToken getByParams(LoginToken params);

    /**
     * 功能描述: 根据微信用户id更新token状态为无效
     *
     * @param updateToken
     * @return: int
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-13 15:48:05
     */
    int updateStatusByWechatUserId(LoginToken updateToken);

    WeChatUser getUserInfoByToken(String accessToken);
}