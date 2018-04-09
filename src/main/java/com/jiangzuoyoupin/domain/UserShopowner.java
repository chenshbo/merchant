package com.jiangzuoyoupin.domain;

import java.util.Date;

public class UserShopowner {
    private Long id;

    private Long wechatUserId;

    private String mobileNo;

    private Long provinceId;

    private Long cityId;

    private String idCardFrontImage;

    private String idCardObverseImage;

    private Long companyAccountId;

    private Double registerAmount;

    private String remittanceCertificateImage;

    private String loginName;

    private String loginPwd;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getIdCardFrontImage() {
        return idCardFrontImage;
    }

    public void setIdCardFrontImage(String idCardFrontImage) {
        this.idCardFrontImage = idCardFrontImage == null ? null : idCardFrontImage.trim();
    }

    public String getIdCardObverseImage() {
        return idCardObverseImage;
    }

    public void setIdCardObverseImage(String idCardObverseImage) {
        this.idCardObverseImage = idCardObverseImage == null ? null : idCardObverseImage.trim();
    }

    public Long getCompanyAccountId() {
        return companyAccountId;
    }

    public void setCompanyAccountId(Long companyAccountId) {
        this.companyAccountId = companyAccountId;
    }

    public Double getRegisterAmount() {
        return registerAmount;
    }

    public void setRegisterAmount(Double registerAmount) {
        this.registerAmount = registerAmount;
    }

    public String getRemittanceCertificateImage() {
        return remittanceCertificateImage;
    }

    public void setRemittanceCertificateImage(String remittanceCertificateImage) {
        this.remittanceCertificateImage = remittanceCertificateImage == null ? null : remittanceCertificateImage.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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