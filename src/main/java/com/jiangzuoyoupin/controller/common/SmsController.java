package com.jiangzuoyoupin.controller.common;

import com.alibaba.fastjson.JSON;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.SmsResult;
import com.jiangzuoyoupin.domain.SmsVerifyCode;
import com.jiangzuoyoupin.req.SendVerifyCodeReq;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.utils.DateUtil;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 功能描述: 短信模块
 *
 * @author: chenshangbo
 * @date: 2018-4-9 21:21:39
 */
@Api("短信模块")
@RestController
@RequestMapping("common/sms")
public class SmsController {

    @Value("${sms.send.url}")
    private String smsSendUrl;
    //短信内容，注意内容中的逗号请使用中文状态下的逗号
    private final static String CONTENT = "验证码%s，您正在进行注册操作，验证码有效期10分钟。";
    private final static Integer VERIFY_CODE_EXPIRE_MIN = 10;

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
        if(!smsService.checkSendVerifyCodeCount(req.getMobileNo())){
            return WebResultUtil.returnErrMsgResult("今日发送短信验证码次数已到达上限，请明天再试");
        }
        String verifyCode = NumberUtil.getVerifyCode();
        String msgContent = String.format(CONTENT, verifyCode);
        String sendUrl = String.format(smsSendUrl, req.getMobileNo(), msgContent);
        String jsonRes = HttpUtil.doGet(sendUrl);
        SmsResult smsResult = JSON.parseObject(jsonRes, SmsResult.class);
        if (smsResult == null || smsResult.getResult() != 0) {
            return WebResultUtil.returnErrMsgResult("发送短信验证码失败");
        }
        SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
        smsVerifyCode.setMobileNo(req.getMobileNo());
        smsVerifyCode.setType(req.getType());
        smsVerifyCode.setVerifyCode(verifyCode);
        Date date = new Date();
        smsVerifyCode.setGmtCreate(date);
        smsVerifyCode.setExpireInDate(DateUtil.addMinutes(date,VERIFY_CODE_EXPIRE_MIN));
        smsService.saveVerifyCode(smsVerifyCode);
        return WebResultUtil.returnResult();
    }

}
