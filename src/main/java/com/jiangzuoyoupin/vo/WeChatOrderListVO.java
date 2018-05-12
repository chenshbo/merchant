package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 收入支出账单vo
 *
 * @author chenshangbo
 * @date 2018-05-12 23:43:30
 */
public class WeChatOrderListVO implements Serializable {

    private static final long serialVersionUID = -3187122771974040299L;

    @ApiModelProperty(value = "金额")
    private Double totalFee;

    @ApiModelProperty(value = "时间")
    private String payTimeEnd;

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(String payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }
}
