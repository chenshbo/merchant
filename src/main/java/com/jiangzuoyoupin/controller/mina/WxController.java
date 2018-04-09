package com.jiangzuoyoupin.controller.mina;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.WechatUser;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 微信api controller
 *
 * @author: chenshangbo
 * @date: 2018/4/9 15:06
 */
@Api("微信API模块")
@RestController
@RequestMapping("mina/wx")
public class WxController {

    @Autowired
    private UserService userService;

    /**
     * 功能模块: TODO
     *
      * @param code
     * @param state
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 21:12:36
     */
    @ApiOperation(value = "微信注册授权回调", notes = "微信注册授权回调")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value="微信认证code",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "state",value="state",paramType = "query",dataType = "String")
    })
    @GetMapping(value = "/login")
    public WebResult wxLogin(@RequestParam(value = "code") String code,
                                       @RequestParam(value = "state") String state) {
        String wxAppId = "wxbc9bba38a8e1f3fc";
        String wxAppSecret = "a4856d46f1f4035a1911ae2aaa17d102";
        String access_token = "";
        String openid = "";

        // 通过code获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + wxAppId
                + "&secret=" + wxAppSecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        String tokenInfo = HttpUtil.doGet(url);
        JSONObject json = JSONObject.parseObject(tokenInfo);
        if (!json.containsKey("errcode")) {
            access_token = json.getString("access_token");// 接口调用凭证
            openid = json.getString("openid");// 授权用户唯一标识
        }

        // 拉取用户信息
        String uuidUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid=" + openid;
        String wxUserInfo = HttpUtil.doGet(uuidUrl);
        JSONObject jsonObj = JSONObject.parseObject(wxUserInfo);
        WechatUser wechat = new WechatUser();
        if (jsonObj != null && !jsonObj.containsKey("errcode")) {
            wechat.setNickName(jsonObj.getString("nickname"));
            wechat.setHeadImgUrl(jsonObj.getString("headimgurl"));
            wechat.setUnionId(jsonObj.getString("unionid"));
            wechat.setOpenId(openid);
        }
        userService.saveWxUserInfo(wechat);
        return WebResultUtil.returnResult();
    }




}
