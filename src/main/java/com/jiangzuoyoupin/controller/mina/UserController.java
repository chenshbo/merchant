package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.UserFans;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.req.UserFansRegReq;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 用户controller
 *
 * @author chenshangbo
 * @date 2018/4/9
 */
@Auth
@Api("小程序-用户模块")
@RestController
@RequestMapping("mina/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;


    /**
     * 功能模块: 发送短信验证码到手机
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "粉丝注册", notes = "粉丝注册")
    @ApiImplicitParam(name = "req", value = "粉丝注册对象", dataType = "UserFansRegReq")
    @PostMapping(value = "/registerFans")
    public WebResult registerFans(@RequestBody UserFansRegReq req, HttpServletRequest request) {
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if(StringUtils.isNotEmpty(errMsg)){
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        UserFans fans = new UserFans();
        BeanUtils.copyProperties(req, fans);
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if(weChatUser == null){
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        fans.setWechatUserId(weChatUser.getId());
        int res = userService.registerFans(fans);
        if(res == 0){
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    private WeChatUser getWeChatUserByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        return userService.getUserInfoByToken(accessToken);
    }


}
