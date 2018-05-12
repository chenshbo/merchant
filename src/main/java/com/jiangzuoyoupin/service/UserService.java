package com.jiangzuoyoupin.service;

import com.alibaba.fastjson.JSONObject;
import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.mapper.*;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
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
    private ShopManagerMapper shopManagerMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private LoginTokenMapper loginTokenMapper;

    private static String path = "pages/index?id=";
    @Value("${wx.appid}")
    private String wxAppId;
    @Value("${wx.secret}")
    private String wxAppSecret;
    @Value("${web.upload.path}")
    private String webUploadPath;
    @Value("${images.path}")
    private String imagesPath;

    public boolean checkLoginToken(String accessToken) {

        LoginToken params = new LoginToken();
        params.setAccessToken(accessToken);
        LoginToken result = loginTokenMapper.getByParams(params);
        // 不存在
        if (result == null) {
            return false;
        }
        // 已过期
        if (new Date().after(result.getAccessTokenExpires())) {
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
    public LoginToken generateToken(WeChatUser wechat) {
        int result = 0;
        LoginToken loginToken = null;
        WeChatUser params = new WeChatUser();
        params.setOpenId(wechat.getOpenId());
        WeChatUser byParams = weChatUserMapper.getByParams(params);
        int role = 0;
        Long shopId = 0l;
        int isOpenPermissions = 0;
        // openId存在 更新数据
        if (byParams != null) {
            if (StringUtils.isNotEmpty(byParams.getMobileNo())) {
                role = 1;
            }
            wechat.setId(byParams.getId());
//            result = weChatUserMapper.updateByPrimaryKeySelective(wechat);
            result = 1;
            Shop shop = shopMapper.selectByWeChatUserId(byParams.getId());
            if (shop != null) {
                role = 2;
                shopId = shop.getId();
                isOpenPermissions = shop.getIsOpenPermissions();
            } else {
                ShopManager manager = shopManagerMapper.selectByWeChatUserId(byParams.getId());
                if (manager != null) {
                    role = 3;
                    shopId = manager.getShopId();
                    Shop managerShop = shopMapper.selectByPrimaryKey(manager.getShopId());
                    if (managerShop != null) {
                        isOpenPermissions = managerShop.getIsOpenPermissions();
                    }
                }
            }
        } else {
            result = weChatUserMapper.insert(wechat);
        }
        // 店铺id为空
        if (shopId.equals(0L)) {
            List<Shop> shopList = shopMapper.selectAll();
            Integer random = NumberUtil.getRandomByRange(shopList.size());
            shopId = shopList.get(random.intValue()).getId();
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
            loginToken.setIsOpenPermissions(isOpenPermissions);
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
        if (count > 0) {
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
        if (count > 0) {
            updateWeChatUserMobileNo(shop.getWechatUserId(), shop.getMobileNo());
            String qrCode = createQrCode(path, shop.getId());
            Shop update = new Shop();
            update.setId(shop.getId());
            update.setQrCodeImg(qrCode);
            shopMapper.updateByPrimaryKeySelective(update);
        }
        return shop.getId();
    }

    public String createQrCode(String path, Long id) {
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxAppId + "&secret=" + wxAppSecret;
        String tokenInfo = HttpUtil.doGet(getTokenUrl);
        JSONObject tokenJson = JSONObject.parseObject(tokenInfo);
        if (!tokenJson.containsKey("errcode")) {
            String access_token = tokenJson.getString("access_token");// 接口调用凭证
            String getQrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;
            String params = "{\"path\":\"" + path + id + "\",\"width\":430}";
            byte[] qrCodeInfo = null;
            String newFileName = "qr" + id + ".png";
            try {
                qrCodeInfo = HttpUtil.getByteByPost(getQrCodeUrl, params);
                try {
                    File targetFile = new File(webUploadPath + imagesPath);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(webUploadPath + imagesPath + "/" + newFileName);
                    out.write(qrCodeInfo);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imagesPath + "/" + newFileName;
        } else {
            return "";
        }
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

    public WeChatUser getWechatUserByMobileNo(String customMobileNo) {
        WeChatUser params = new WeChatUser();
        params.setMobileNo(customMobileNo);
        return weChatUserMapper.getByParams(params);
    }

    public WeChatUser getUserInfoById(Long weChatUserId) {
        return weChatUserMapper.selectByPrimaryKey(weChatUserId);
    }

    public int saveWxUserInfo(WeChatUser user) {
        return weChatUserMapper.updateByPrimaryKeySelective(user);
    }
}
