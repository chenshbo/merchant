package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.SmsVerifyCode;

public interface SmsVerifyCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsVerifyCode record);

    int insertSelective(SmsVerifyCode record);

    SmsVerifyCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsVerifyCode record);

    int updateByPrimaryKey(SmsVerifyCode record);

    SmsVerifyCode getByParams(SmsVerifyCode smsVerifyCode);

    int checkSendVerifyCodeCount(SmsVerifyCode smsVerifyCode);
}