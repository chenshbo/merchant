package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 榜单列表vo
 *
 * @author chenshangbo
 * @date 2018-05-06 16:42:57
 */
public class BillListVO implements Serializable{

    private static final long serialVersionUID = -6182015199576425357L;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "消费金额")
    private Double amount;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
