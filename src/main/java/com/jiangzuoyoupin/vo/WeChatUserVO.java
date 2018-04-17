package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

public class WeChatUserVO {

    @ApiModelProperty(value = "微信昵称")
    private String nickName;
    @ApiModelProperty(value = "头像url")
    private String avatarUrl;
    @ApiModelProperty(value = "性别，1男2女0未知")
    private Integer gender;

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