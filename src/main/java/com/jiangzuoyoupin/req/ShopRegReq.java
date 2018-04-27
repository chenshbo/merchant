package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 店主注册req
 *
 * @author chenshangbo
 * @date 2018-04-24 23:01:26
 */
public class ShopRegReq implements Serializable{

    private static final long serialVersionUID = -2393488416940569268L;

    @ApiModelProperty(value = "微信用户ID",required = true)
    private Long wechatUserId;

    @ApiModelProperty(value = "手机号码",required = true)
    private String mobileNo;

    @ApiModelProperty(value = "验证码",required = true)
    private String verifyCode;

    @ApiModelProperty(value = "省id",required = true)
    private Long provinceId;

    @ApiModelProperty(value = "市id",required = true)
    private Long cityId;

    @ApiModelProperty(value = "区县id",required = true)
    private Long areaId;

    @ApiModelProperty(value = "营业执照",required = true)
    private String businessLicenseImage;

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
        this.mobileNo = mobileNo;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage;
    }
}