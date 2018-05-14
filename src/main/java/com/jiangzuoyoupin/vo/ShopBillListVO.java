package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能模块: 店铺消费列表vo
 *
 * @author chenshangbo
 * @date 2018-05-05 23:42:42
 */
public class ShopBillListVO implements Serializable{

    private static final long serialVersionUID = 6647593762806244505L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "客户微信id")
    private Long customWeChatUserId;
    @ApiModelProperty(value = "客户名称")
    private String customName;
    @ApiModelProperty(value = "消费金额")
    private Double amount;
    @ApiModelProperty(value = "奖金池比例")
    private Integer bonusRatio;
    @ApiModelProperty(value = "奖金池金额")
    private Double reward;
    @ApiModelProperty(value = "账单状态，0待审核1审核通过2转账3提现4完成")
    private Integer status;
    @ApiModelProperty(value = "排序状态，0待位1达标2完成")
    private Integer sortStatus;
    @ApiModelProperty(value = "完成时间")
    private Date gmtModified;

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
}
