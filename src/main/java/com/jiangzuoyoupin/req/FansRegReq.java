package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 功能模块: 粉丝注册请求req
 *
 * @author chenshangbo
 * @date 2018-04-09 22:05:08
 */
public class FansRegReq implements Serializable{

    private static final long serialVersionUID = 7442951154430478738L;
    
    @ApiModelProperty(value = "微信用户ID",required = true)
    private Long wechatUserId;

    @ApiModelProperty(value = "手机号码",required = true)
    private String mobileNo;

    @ApiModelProperty(value = "验证码",required = true)
    private String verifyCode;

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}