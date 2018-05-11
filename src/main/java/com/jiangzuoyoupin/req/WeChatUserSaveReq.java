package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能描述: 保存微信用户信息req
 *
 * @author: chenshangbo
 * @date: 2018-05-11 11:18:16
 */
public class WeChatUserSaveReq implements Serializable{

    private static final long serialVersionUID = -5484779572200573650L;

    @ApiModelProperty(value = "用户id",required = true)
    private Long id;
    @ApiModelProperty(value = "昵称",required = true)
    private String nickName;
    @ApiModelProperty(value = "头像",required = true)
    private String avatarUrl;
    @ApiModelProperty(value = "性别，1男2女0未知")
    private Integer gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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