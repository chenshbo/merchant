package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 微信用户授权登录req
 *
 * @author chenshangbo
 * @date 2018-04-12 22:58:35
 */
public class WeChatUserLoginReq implements Serializable{

    private static final long serialVersionUID = 6648914683099723270L;

    @ApiModelProperty(value = "授权码",required = true)
    private String code;
    @ApiModelProperty(value = "昵称",required = true)
    private String nickName;
    @ApiModelProperty(value = "头像",required = true)
    private String avatarUrl;
    @ApiModelProperty(value = "性别，1男2女0未知")
    private Integer gender;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}