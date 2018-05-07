package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 功能模块: 总账单vo
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class ShopBillTotalVO {

    @ApiModelProperty(value = "总销售额")
    private Double totalAmount;
    @ApiModelProperty(value = "总奖金池")
    private Double totalReward;
    @ApiModelProperty(value = "免单人数")
    private Integer freeCount;

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
}
