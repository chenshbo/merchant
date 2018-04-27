package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SupplierVO extends WeChatUserVO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "手机号码")
    private String mobileNo;
    @ApiModelProperty(value = "营业执照图片")
    private String businessLicenseImage;
    @ApiModelProperty(value = "审核状态，0-未审核1-审核通过2-审核驳回")
    private Integer status;
    @ApiModelProperty(value = "供应商后台账号")
    private String loginName;
    @ApiModelProperty(value = "供应商后台密码")
    private String loginPwd;
    @ApiModelProperty(value = "申请时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "审核时间")
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage == null ? null : businessLicenseImage.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}