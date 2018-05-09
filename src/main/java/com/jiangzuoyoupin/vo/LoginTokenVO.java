package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能模块: token vo
 *
 * @author chenshangbo
 * @date 2018-04-12 23:31:45
 */
public class LoginTokenVO implements Serializable{

    private static final long serialVersionUID = 1864025659559496497L;
    
    @ApiModelProperty(value = "微信用户id")
    private Long wechatUserId;

    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "token过期时间")
    private Date accessTokenExpires;

    @ApiModelProperty(value = "角色，0游客1粉丝2店主3匠探")
    private Integer role;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "店铺id")
    private String moduleAuthStr;

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAccessTokenExpires() {
        return accessTokenExpires;
    }

    public void setAccessTokenExpires(Date accessTokenExpires) {
        this.accessTokenExpires = accessTokenExpires;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}