package com.jiangzuoyoupin.service;

import com.alibaba.fastjson.JSON;
import com.jiangzuoyoupin.domain.SmsResult;
import com.jiangzuoyoupin.domain.SmsVerifyCode;
import com.jiangzuoyoupin.mapper.SmsVerifyCodeMapper;
import com.jiangzuoyoupin.req.SendVerifyCodeReq;
import com.jiangzuoyoupin.utils.DateUtil;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 功能模块: 短信service
 *
 * @author chenshangbo
 * @date 2018-04-09 21:45:38
 */
@Service
public class SmsService {

    @Value("${sms.send.url}")
    private String smsSendUrl;
    //短信内容，注意内容中的逗号请使用中文状态下的逗号
    private final static String CONTENT = "验证码%s，您正在进行注册操作，验证码有效期10分钟。";
    private final static Integer VERIFY_CODE_EXPIRE_MIN = 10;
    // 每个用户每天发送验证码的上限
    @Value("${sms.day.limit}")
    private int smsDayLimit;

    @Autowired
    private SmsVerifyCodeMapper smsVerifyCodeMapper;

    /**
     * 功能模块: 验证发送验证码日上限
     *
     * @param mobileNo
     * @return boolean
     * @author chenshangbo
     * @date 2018-04-09 22:39:00
     */
    public boolean checkSendVerifyCodeCount(String mobileNo) {
        SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
        smsVerifyCode.setMobileNo(mobileNo);
        int count = smsVerifyCodeMapper.checkSendVerifyCodeCount(smsVerifyCode);
        if(count < smsDayLimit){
            return true;
        }
        return false;

    }
    /**
     * 功能模块: 保存验证码信息
     *
     * @param smsVerifyCode
     * @return int
     * @author chenshangbo
     * @date 2018-04-09 21:14:34
     */
    public int saveVerifyCode(SmsVerifyCode smsVerifyCode) {
        return smsVerifyCodeMapper.insert(smsVerifyCode);
    }

    /**
     * 功能模块: 判断验证码
     *
     *
     * @param mobileNo
     * @param verifyCode
     * @return java.lang.String
     * @author chenshangbo
     * @date 2018-04-09 22:18:05
     */
    public String checkVerifyCode(String mobileNo, String verifyCode){
        SmsVerifyCode params = new SmsVerifyCode();
        params.setMobileNo(mobileNo);
        params.setVerifyCode(verifyCode);
        SmsVerifyCode smsVerifyCode = smsVerifyCodeMapper.getByParams(params);
        // 短信验证码无效
        if(smsVerifyCode == null){
            return "短信验证码无效";
        }
        // 已过期
        if(new Date().after(smsVerifyCode.getExpireInDate())){
            return "短信验证码已过期";
        }
        return null;
    }

    public int sendVerifyCode(SendVerifyCodeReq req) {
        String verifyCode = NumberUtil.getVerifyCode();
        String msgContent = String.format(CONTENT, verifyCode);
        String sendUrl = String.format(smsSendUrl, req.getMobileNo(), msgContent);
        String jsonRes = HttpUtil.doGet(sendUrl);
        SmsResult smsResult = JSON.parseObject(jsonRes, SmsResult.class);
        if (smsResult == null || smsResult.getResult() != 0) {
            return 0;
        }
        SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
        smsVerifyCode.setMobileNo(req.getMobileNo());
        smsVerifyCode.setType(req.getType());
        smsVerifyCode.setVerifyCode(verifyCode);
        Date date = new Date();
        smsVerifyCode.setGmtCreate(date);
        smsVerifyCode.setExpireInDate(DateUtil.addMinutes(date, VERIFY_CODE_EXPIRE_MIN));
        return smsVerifyCodeMapper.insert(smsVerifyCode);
    }
}
