package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 我的消费账单vo
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class MyBillTotalVO implements Serializable{

    private static final long serialVersionUID = 4041804775200656816L;

    @ApiModelProperty(value = "待免单总额")
    private Double totalAmount;
    @ApiModelProperty(value = "待免单人数")
    private Integer freeCount;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }
}
