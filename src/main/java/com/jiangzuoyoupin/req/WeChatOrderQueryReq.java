package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 微信账单查询req
 *
 * @author chenshangbo
 * @date 2018-05-06 16:34:52
 */
public class WeChatOrderQueryReq implements Serializable{

    private static final long serialVersionUID = -6188186544614532278L;

    @ApiModelProperty(value = "用户id")
    private Long weChatUserId;

    @ApiModelProperty(value = "查询类型，2收入3支出")
    private Integer type;

    public Long getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(Long weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}