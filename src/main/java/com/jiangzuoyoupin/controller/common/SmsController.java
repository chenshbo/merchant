package com.jiangzuoyoupin.controller.common;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.req.SendVerifyCodeReq;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 短信模块
 *
 * @author: chenshangbo
 * @date: 2018-4-9 21:21:39
 */
@Auth
@Api("公共-短信模块")
@RestController
@RequestMapping("common/sms")
public class SmsController extends BaseController {

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
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码到手机")
    @ApiImplicitParam(name = "req", value = "发送短信请求对象", dataType = "SendVerifyCodeReq")
    @PostMapping(value = "/sendVerifyCode")
    public WebResult sendVerifyCode(@RequestBody SendVerifyCodeReq req) {
        int res = smsService.sendVerifyCode(req);
        if(res == 0){
            return WebResultUtil.returnErrMsgResult("发送验证码失败");
        }
        return WebResultUtil.returnResult();
    }

}
