package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 预下单 req
 *
 * @author chenshangbo
 * @date 2018-05-06 16:34:52
 */
public class PrepayReq implements Serializable{

    private static final long serialVersionUID = -5388205948712258465L;

    @ApiModelProperty(value = "用户id")
    private Long weChatUserId;
    @ApiModelProperty(value = "支付金额 单位分")
    private Integer amount;

    public Long getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(Long weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}