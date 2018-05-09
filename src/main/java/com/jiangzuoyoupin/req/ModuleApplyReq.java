package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 功能开通req
 *
 * @author chenshangbo
 * @date 2018-04-24 23:22:54
 */
public class ModuleApplyReq implements Serializable{

    private static final long serialVersionUID = -3082495404275542986L;

    @ApiModelProperty(value = "店铺id",required = true)
    private Long shopId;

    @ApiModelProperty(value = "申请类型 1支付开通2邀请码开通",required = true)
    private Integer applyType;

    @ApiModelProperty(value = "邀请码")
    private String invitationCode;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}