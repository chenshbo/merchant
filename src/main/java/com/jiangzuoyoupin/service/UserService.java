package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.mapper.LoginTokenMapper;
import com.jiangzuoyoupin.mapper.ShopMapper;
import com.jiangzuoyoupin.mapper.SupplierMapper;
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
    private ShopManagerMapper shopManagerMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ShopMapper shopMapper;

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
        int role = 0;
        Long shopId = 0l;
        // openId存在 更新数据
        if(byParams != null){
            wechat.setId(byParams.getId());
            result = weChatUserMapper.updateByPrimaryKeySelective(wechat);
            Shop shop = shopMapper.selectByWeChatUserId(byParams.getId());
            if(shop != null){
                role = 1;
                shopId = shop.getId();
            }else {
                ShopManager manager = shopManagerMapper.selectByWeChatUserId(byParams.getId());
                if (manager != null) {
                    role = 2;
                    shopId = manager.getShopId();
                }
            }
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
            loginToken.setRole(role);
            loginToken.setShopId(shopId);
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

    public WeChatUser getUserInfoByToken(String accessToken) {
        return loginTokenMapper.getUserInfoByToken(accessToken);
    }

    /**
     * 功能模块: 粉丝注册
     *
     * @param fans
     * @return int
     * @author chenshangbo
     * @date 2018-04-09 22:08:49
     */
    public int registerFans(Fans fans) {
        return updateWeChatUserMobileNo(fans.getWechatUserId(), fans.getMobileNo());
    }

    /**
     * 功能模块: 注册供应商
     *
     * @param supplier
     * @return int
     * @author chenshangbo
     * @date 2018-04-20 23:30:00
     */
    public int registerSupplier(Supplier supplier) {
        int count = supplierMapper.insert(supplier);
        if(count > 0){
            updateWeChatUserMobileNo(supplier.getWechatUserId(), supplier.getMobileNo());
        }
        return count;
    }

    /**
     * 功能模块: 保存店主信息
     *
     * @param shop
     * @return int
     * @author chenshangbo
     * @date 2018-04-24 23:06:01
     */
    public Long registerShop(Shop shop) {
        int count = shopMapper.insert(shop);
        if(count > 0){
            updateWeChatUserMobileNo(shop.getWechatUserId(), shop.getMobileNo());
        }
        return shop.getId();
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

    public WeChatUser getWechatUserByMobileNo(String customMobileNo){
        WeChatUser params = new WeChatUser();
        params.setMobileNo(customMobileNo);
        return weChatUserMapper.getByParams(params);
    }

}
