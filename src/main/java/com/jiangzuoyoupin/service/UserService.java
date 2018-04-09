package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.UserFans;
import com.jiangzuoyoupin.domain.WechatUser;
import com.jiangzuoyoupin.mapper.UserFansMapper;
import com.jiangzuoyoupin.mapper.WechatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能模块: 用户service
 *
 * @author chenshangbo
 * @date 2018-4-9 21:15:55
 */
@Service
public class UserService {

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Autowired
    private UserFansMapper userFansMapper;

    /**
     * 功能模块: 保存微信用户信息
     *
     * @param wechat
     * @return int
     * @author chenshangbo
     * @date 2018-04-09 21:14:34
     */
    public int saveWxUserInfo(WechatUser wechat) {
        return wechatUserMapper.insert(wechat);
    }

    /**
     * 功能模块: 粉丝注册
     *
     * @param fans
     * @return int
     * @author chenshangbo
     * @date 2018-04-09 22:08:49
     */
    public int registerFans(UserFans fans) {
        return userFansMapper.insert(fans);
    }
}
