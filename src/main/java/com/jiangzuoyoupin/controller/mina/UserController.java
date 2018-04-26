package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.req.SendVerifyCodeReq;
import com.jiangzuoyoupin.req.UserFansRegReq;
import com.jiangzuoyoupin.req.UserShopownerRegReq;
import com.jiangzuoyoupin.req.UserSupplierRegReq;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class UserController extends BaseController {

    @Value("${images.path}")
    private String imagesPath;

    @Value("${root.url}")
    private String rootUrl;

    @Autowired
    private SmsService smsService;

    /**
     * 功能模块: 粉丝注册
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "粉丝注册", notes = "粉丝注册")
    @ApiImplicitParam(name = "req", value = "粉丝注册对象", dataType = "UserFansRegReq")
    @PostMapping(value = "/fans/register")
    public WebResult registerFans(@RequestBody UserFansRegReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        UserFans fans = new UserFans();
        BeanUtils.copyProperties(req, fans);
        fans.setWechatUserId(weChatUser.getId());
        int res = userService.registerFans(fans);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 供应商注册
     *
     * @param req
     * @param request
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-18 22:39:39
     */
    @ApiOperation(value = "供应商注册", notes = "供应商注册")
    @ApiImplicitParam(name = "req", value = "供应商注册对象", dataType = "UserSupplierRegReq")
    @PostMapping(value = "/supplier/register")
    public WebResult registerSupplier(@RequestBody UserSupplierRegReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        UserSupplier supplier = new UserSupplier();
        BeanUtils.copyProperties(req, supplier);
        supplier.setStatus(0);
        supplier.setBusinessLicenseImage(imagesPath + "/" + supplier.getBusinessLicenseImage());
        supplier.setWechatUserId(weChatUser.getId());
        int res = userService.registerSupplier(supplier);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 店主注册申请
     *
     * @param req
     * @param request
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:02:51
     */
    @ApiOperation(value = "店主注册申请", notes = "店主注册申请")
    @ApiImplicitParam(name = "req", value = "店主注册对象", dataType = "UserShopownerRegReq")
    @PostMapping(value = "/shopowner/register")
    public WebResult registerShopowner(@RequestBody UserShopownerRegReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        UserShopowner shopowner = new UserShopowner();
        BeanUtils.copyProperties(req, shopowner);
        shopowner.setWechatUserId(weChatUser.getId());
        shopowner.setStatus(1);
        shopowner.setBusinessLicenseImage(imagesPath + "/" + shopowner.getBusinessLicenseImage());
        int res = userService.registerShopowner(shopowner);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 发送短信验证码到手机
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码到手机")
    @ApiImplicitParam(name = "req", value = "发送短信请求对象", dataType = "SendVerifyCodeReq")
    @PostMapping(value = "/sendVerifyCode")
    public WebResult sendVerifyCode(@RequestBody SendVerifyCodeReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        if (StringUtils.isNotEmpty(weChatUser.getMobileNo())
                && !weChatUser.getMobileNo().equals(req.getMobileNo())) {
            return WebResultUtil.returnErrMsgResult("手机号码与微信注册时不一致，请重新输入");
        }
        if (!smsService.checkSendVerifyCodeCount(req.getMobileNo())) {
            return WebResultUtil.returnErrMsgResult("今日发送短信验证码次数已到达上限，请明天再试");
        }
        int res = smsService.sendVerifyCode(req);
        if(res == 0){
            return WebResultUtil.returnErrMsgResult("发送验证码失败");
        }
        return WebResultUtil.returnResult();
    }

}
