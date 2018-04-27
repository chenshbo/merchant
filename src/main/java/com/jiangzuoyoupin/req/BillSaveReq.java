package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能描述: 账单保存表
 *
 * @author: chenshangbo
 * @date: 2018-04-27 15:16:41
 */
public class BillSaveReq implements Serializable{

    private static final long serialVersionUID = 8043924278854300741L;

    @ApiModelProperty(value = "客户名称",required = true)
    private Long shopId;
    @ApiModelProperty(value = "客户名称",required = true)
    private Long weChatUserId;
    @ApiModelProperty(value = "客户名称",required = true)
    private String customName;
    @ApiModelProperty(value = "昵称",required = true)
    private String customMobileNo;
    @ApiModelProperty(value = "消费金额",required = true)
    private Double amount;
    @ApiModelProperty(value = "奖金比例",required = true)
    private Integer bonusRatio;
    @ApiModelProperty(value = "奖金")
    private Double reward;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(Long weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomMobileNo() {
        return customMobileNo;
    }

    public void setCustomMobileNo(String customMobileNo) {
        this.customMobileNo = customMobileNo;
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
}