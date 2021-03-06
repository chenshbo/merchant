package com.jiangzuoyoupin.dto;

import com.jiangzuoyoupin.domain.ShopBill;

/**
 * 功能模块: 账单dto
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class ShopBillDto extends ShopBill{

    private Double totalAmount;
    private Double totalReward;
    private Integer freeCount;

    private String nickName;
    private Integer rank;
    private String avatarUrl;
    private String openId;
    private Long shopOwnerUserId;

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

    public Integer getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getShopOwnerUserId() {
        return shopOwnerUserId;
    }

    public void setShopOwnerUserId(Long shopOwnerUserId) {
        this.shopOwnerUserId = shopOwnerUserId;
    }
}
