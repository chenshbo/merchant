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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}