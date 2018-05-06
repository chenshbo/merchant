package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 店铺id微信id req
 *
 * @author chenshangbo
 * @date 2018-05-06 16:34:52
 */
public class ShopIdAndWeChatUserIdReq implements Serializable{

    private static final long serialVersionUID = 1772633177549657651L;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;
    @ApiModelProperty(value = "用户id")
    private Long weChatUserId;

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
}