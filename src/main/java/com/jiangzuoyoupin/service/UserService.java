package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.LoginToken;
import com.jiangzuoyoupin.domain.UserFans;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.mapper.LoginTokenMapper;
import com.jiangzuoyoupin.mapper.UserFansMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import com.jiangzuoyoupin.utils.TokenUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 功能模块: 用户service
 *
 * @author chenshangbo
 * @date 2018-4-9 21:15:55
 */
@Service
public class UserService {

    // token过期天数
    private final static Integer ACCESS_TOKEN_EXPIRE_DAY = 30;

    @Autowired
    private WeChatUserMapper weChatUserMapper;

    @Autowired
    private UserFansMapper userFansMapper;

    @Autowired
    private LoginTokenMapper loginTokenMapper;

    /**
     * 功能模块: 保存微信用户信息并生成token
     *
     * @param wechat
     * @return LoginToken
     * @author chenshangbo
     * @date 2018-04-09 21:14:34
     */
    public LoginToken saveWxUserInfoAndGenerateToken(WeChatUser wechat) {
        LoginToken loginToken = null;
        int result = weChatUserMapper.insert(wechat);
        if (result > 0) {
            // 生成登录信息
            loginToken = new LoginToken();
            loginToken.setWechatUserId(wechat.getId());
            loginToken.setAccessToken(TokenUtil.generateToken());
            Date date = new Date();
            loginToken.setGmtCreate(date);
            loginToken.setGmtModified(date);
            loginToken.setAccessTokenExpires(DateUtils.addDays(date, ACCESS_TOKEN_EXPIRE_DAY));
            loginTokenMapper.insert(loginToken);
        }
        return loginToken;
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
