package com.jiangzuoyoupin.dto;

import java.util.Date;

/**
 * 功能模块: 账单dto
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class ShopBillDto {

    private Long id;
    private Long shopId;
    private Double totalAmount;
    private Double totalReward;

    private Long customWeChatUserId;
    private String customName;
    private Double amount;
    private Integer status;
    private Integer sortStatus;
    private Date gmtModified;

    private String nickName;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Double totalReward) {
        this.totalReward = totalReward;
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
        this.customName = customName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
