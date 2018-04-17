package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能描述: 管理员登录req
 *
 * @author: chenshangbo
 * @date: 2018-04-17 20:55:28
 */
public class AdminLoginReq implements Serializable{

    private static final long serialVersionUID = -6292431913132052917L;
    
    @ApiModelProperty(value = "登录名",required = true)
    private String loginName;
    @ApiModelProperty(value = "登录密码",required = true)
    private String loginPwd;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}