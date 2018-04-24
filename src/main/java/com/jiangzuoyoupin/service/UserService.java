package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.mapper.*;
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
    private UserSupplierMapper userSupplierMapper;

    @Autowired
    private UserShopownerMapper userShopownerMapper;

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
        int count = userFansMapper.insert(fans);
        if(count > 0){
            updateWeChatUserMobileNo(fans.getWechatUserId(), fans.getMobileNo());
        }
        return count;
    }

    public WeChatUser getUserInfoByToken(String accessToken) {
        return loginTokenMapper.getUserInfoByToken(accessToken);
    }

    /**
     * 功能模块: 注册供应商
     *
     * @param supplier
     * @return int
     * @author chenshangbo
     * @date 2018-04-20 23:30:00
     */
    public int registerSupplier(UserSupplier supplier) {
        int count = userSupplierMapper.insert(supplier);
        if(count > 0){
            updateWeChatUserMobileNo(supplier.getWechatUserId(), supplier.getMobileNo());
        }
        return count;
    }

    /**
     * 功能模块: 手机号为空更新手机号
     *
     * @param id
     * @param mobileNo
     * @return int
     * @author chenshangbo
     * @date 2018-04-20 23:22:58
     */
    public int updateWeChatUserMobileNo(Long id, String mobileNo) {
        WeChatUser params = new WeChatUser();
        params.setId(id);
        params.setMobileNo(mobileNo);
        return weChatUserMapper.updateMobileNo(params);
    }

    /**
     * 功能模块: 保存店主信息
     *
     * @param shopowner
     * @return int
     * @author chenshangbo
     * @date 2018-04-24 23:06:01
     */
    public int registerShopowner(UserShopowner shopowner) {
        return userShopownerMapper.insert(shopowner);
    }
}
