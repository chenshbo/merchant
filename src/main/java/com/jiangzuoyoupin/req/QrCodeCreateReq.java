package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能描述: 二维码生成
 *
 * @author: chenshangbo
 * @date: 2018-05-08 20:05:25
 */
public class QrCodeCreateReq implements Serializable{

    private static final long serialVersionUID = -6861140779546460547L;

    @ApiModelProperty(value = "二维码指定路径",required = true)
    private String path;
    @ApiModelProperty(value = "店铺id",required = true)
    private Long shopId;
    @ApiModelProperty(value = "微信用户id",required = true)
    private Long weChatUserId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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