package com.jiangzuoyoupin.domain;

import java.util.Date;

public class ShopBill {
    private Long id;

    private Long shopId;

    private Long customWeChatUserId;

    private String customName;

    private String customMobileNo;

    private Double amount;

    private Integer bonusRatio;

    private Double reward;

    private Integer status;

    private Integer sortStatus;

    private Long createWeChatUserId;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCustomWeChatUserId() {
        return customWeChatUserId;
    }

    public void setCustomWeChatUserId(Long customWeChatUserId) {
        this.customWeChatUserId = customWeChatUserId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public String getCustomMobileNo() {
        return customMobileNo;
    }

    public void setCustomMobileNo(String customMobileNo) {
        this.customMobileNo = customMobileNo == null ? null : customMobileNo.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getBonusRatio() {
        return bonusRatio;
    }

    public void setBonusRatio(Integer bonusRatio) {
        this.bonusRatio = bonusRatio;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortStatus() {
        return sortStatus;
    }

    public void setSortStatus(Integer sortStatus) {
        this.sortStatus = sortStatus;
    }

    public Long getCreateWeChatUserId() {
        return createWeChatUserId;
    }

    public void setCreateWeChatUserId(Long createWeChatUserId) {
        this.createWeChatUserId = createWeChatUserId;
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