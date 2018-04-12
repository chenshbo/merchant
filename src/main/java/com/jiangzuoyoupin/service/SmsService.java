package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.SmsVerifyCode;
import com.jiangzuoyoupin.mapper.SmsVerifyCodeMapper;
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
}
