package com.jiangzuoyoupin.controller.common;

import com.bcloud.msg.http.HttpSender;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.City;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.SelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

import java.util.List;

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

    @Value("${sms.host}")
    private String smsHost;
    //示远账号
    @Value("${sms.account}")
    private String account;
    //示远密码
    @Value("${sms.password}")
    private String password;
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
    public WebResult<Boolean> sendVerifyCode(@ApiParam(name = "mobileNo", value = "手机号码", required = true) @PathVariable String mobileNo) {
        //是否需要状态报告，需要true，不需要false
        boolean needStatus = true;
        try {
//            {
//                "ts": "20110725160412",
//                "result": 0,
//                "msgid": "1234567890100"
//            }
            String msgContent = String.format(content, NumberUtil.getVerifyCode());
            String returnString = HttpSender.send(smsHost, account, password, mobileNo, msgContent, needStatus, "", "");
            System.out.println(returnString);
            //TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            //TODO 处理异常
            e.printStackTrace();
        }
        return WebResultUtil.returnResult(true);
    }


}
