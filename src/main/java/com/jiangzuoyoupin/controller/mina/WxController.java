package com.jiangzuoyoupin.controller.mina;

import com.alibaba.fastjson.JSONObject;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.LoginToken;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.req.WeChatUserLoginReq;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.LoginTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 微信api controller
 *
 * @author: chenshangbo
 * @date: 2018/4/9 15:06
 */
@Api("小程序-微信API模块")
@RestController
@RequestMapping("mina/wx")
public class WxController {

    @Autowired
    private UserService userService;

    @Value("${wx.appid}")
    private String wxAppId;
    @Value("${wx.secret}")
    private String wxAppSecret;

    /**
     * 功能模块: 微信授权登录
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 21:12:36
     */
    @ApiOperation(value = "微信授权登录", notes = "根据code换取openid和用户信息，返回用户登录的token")
    @ApiImplicitParam(name = "req", value = "授权登录对象", dataType = "WeChatUserLoginReq")
    @PostMapping(value = "/login")
    public WebResult<LoginTokenVO> login(@RequestBody WeChatUserLoginReq req) {
        String access_token = "";
        String openid = "";

        // 通过code获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxAppId
                + "&secret=" + wxAppSecret
                + "&code=" + req.getCode()
                + "&grant_type=authorization_code";
        String tokenInfo = HttpUtil.doGet(url);
        JSONObject tokenJson = JSONObject.parseObject(tokenInfo);
        if (!tokenJson.containsKey("errcode")) {
            access_token = tokenJson.getString("access_token");// 接口调用凭证
            openid = tokenJson.getString("openid");// 授权用户唯一标识
        } else {
            return WebResultUtil.returnErrMsgResult("获取微信access_token失败，" + tokenJson.getString("errmsg"));
        }

        // 拉取用户信息
        WeChatUser wechat = new WeChatUser();
        BeanUtils.copyProperties(req, wechat);
        wechat.setOpenId(openid);
//        String uuidUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
//                + access_token
//                + "&openid=" + openid;
//        String wxUserInfo = HttpUtil.doGet(uuidUrl);
//        JSONObject wxUserJson = JSONObject.parseObject(wxUserInfo);
//        WechatUser wechat = new WechatUser();
//        if (wxUserJson != null && !wxUserJson.containsKey("errcode")) {
//            wechat.setNickName(wxUserJson.getString("nickname"));
//            wechat.setHeadImgUrl(wxUserJson.getString("headimgurl"));
//            wechat.setUnionId(wxUserJson.getString("unionid"));
//            wechat.setOpenId(openid);
//        }else{
//            return WebResultUtil.returnErrMsgResult("获取微信用户信息失败，"+wxUserJson.getString("errcode"));
//        }
        LoginToken loginToken = userService.saveWxUserInfoAndGenerateToken(wechat);
        if (loginToken == null) {
            return WebResultUtil.returnErrMsgResult("微信授权登录失败");
        }
        LoginTokenVO vo = new LoginTokenVO();
        BeanUtils.copyProperties(loginToken, vo);
        return WebResultUtil.returnResult(vo);
    }

}
