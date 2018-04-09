package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 功能模块: 发送验证码请求对象
 *
 * @author chenshangbo
 * @date 2018-04-09 21:56:11
 */
public class SendVerifyCodeReq implements Serializable {

    private static final long serialVersionUID = 7594683939878087365L;

    @ApiModelProperty(value = "手机号码",required = true)
    private String mobileNo;

    @ApiModelProperty(value = "短信类型，1粉丝2供应商3店主",required = true)
    private Integer type;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}