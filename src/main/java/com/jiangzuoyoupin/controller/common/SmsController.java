package com.jiangzuoyoupin.controller.common;

import com.alibaba.fastjson.JSON;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.SmsResult;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 短信模块
 *
 * @author: chenshangbo
 * @date: 2018/4/9 15:06
 */
@Api("短信模块")
@RestController
@RequestMapping("common/sms")
public class SmsController {

    @Value("${sms.send.url}")
    private String smsSendUrl;
    //短信内容，注意内容中的逗号请使用中文状态下的逗号
    private String content = "验证码%s，您正在进行注册操作，验证码有效期10分钟。";

    /**
     * 功能描述: 发送短信验证码
     *
     * @param mobileNo
     * @return: com.jiangzuoyoupin.base.WebResult<java.lang.Boolean>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018/4/9 15:21
     */
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码到手机")
    @GetMapping(value = "/sendVerifyCode/{mobileNo}")
    public WebResult sendVerifyCode(@ApiParam(name = "mobileNo", value = "手机号码", required = true) @PathVariable String mobileNo) {
        String msgContent = String.format(content, NumberUtil.getVerifyCode());
        String sendUrl = String.format(smsSendUrl, mobileNo, msgContent);
        String jsonRes = HttpUtil.doGet(sendUrl);
        SmsResult smsResult = JSON.parseObject(jsonRes, SmsResult.class);
        if (smsResult == null || smsResult.getResult() != 0) {
            return WebResultUtil.returnErrMsgResult("发送短信验证码失败");
        }
        return WebResultUtil.returnResult();
    }


}
