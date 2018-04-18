package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 功能模块: 供应商注册req
 *
 * @author chenshangbo
 * @date 2018-04-18 22:36:38
 */
public class UserSupplierReqReq implements Serializable{

    private static final long serialVersionUID = -822663160188263239L;

    @ApiModelProperty(value = "手机号码",required = true)
    private String mobileNo;

    @ApiModelProperty(value = "验证码",required = true)
    private String verifyCode;

    @ApiModelProperty(value = "营业执照",required = true)
    private String businessLicenseImage;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage == null ? null : businessLicenseImage.trim();
    }

}