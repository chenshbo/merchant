package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.LoginToken;
import com.jiangzuoyoupin.domain.UserFans;
import com.jiangzuoyoupin.domain.UserSupplier;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.mapper.LoginTokenMapper;
import com.jiangzuoyoupin.mapper.UserFansMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import com.jiangzuoyoupin.req.SupplierQueryReq;
import com.jiangzuoyoupin.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public boolean checkLoginToken(String accessToken){

        LoginToken params = new LoginToken();
        params.setAccessToken(accessToken);
        LoginToken result = loginTokenMapper.getByParams(params);
        // 不存在
        if(result == null){
            return false;
        }
        // 已过期
        if(new Date().after(result.getAccessTokenExpires())){
            return false;
        }
        return true;
    }

    /**
     * 功能模块: 保存微信用户信息并生成token
     *
     * @param wechat
     * @return LoginToken
     * @author chenshangbo
     * @date 2018-04-09 21:14:34
     */
    public LoginToken saveWxUserInfoAndGenerateToken(WeChatUser wechat) {
        int result;
        LoginToken loginToken = null;
        WeChatUser params = new WeChatUser();
        params.setOpenId(wechat.getOpenId());
        WeChatUser byParams = weChatUserMapper.getByParams(params);
        // openId存在 更新数据
        if(byParams != null){
            wechat.setId(byParams.getId());
            result = weChatUserMapper.updateByPrimaryKeySelective(wechat);
        }else{
            result = weChatUserMapper.insert(wechat);
        }
        if (result > 0) {
            // 之前的token无效
            LoginToken updateToken = new LoginToken();
            updateToken.setStatus(0);
            updateToken.setWechatUserId(wechat.getId());
            loginTokenMapper.updateStatusByWechatUserId(updateToken);
            // 重新生成登录信息
            loginToken = new LoginToken();
            loginToken.setWechatUserId(wechat.getId());
            loginToken.setAccessToken(TokenUtil.generateToken());
            loginToken.setStatus(1);
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

    public WeChatUser getUserInfoByToken(String accessToken) {
        return loginTokenMapper.getUserInfoByToken(accessToken);
    }
}
