package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能描述: 邀请匠探req
 *
 * @author: chenshangbo
 * @date: 2018-04-27 15:16:41
 */
public class ManagerAddReq implements Serializable{

    @ApiModelProperty(value = "店铺id",required = true)
    private Long shopId;
    @ApiModelProperty(value = "邀请人手机号码",required = true)
    private String mobileNo;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}