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
}