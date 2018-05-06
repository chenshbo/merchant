package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能模块: 匠探列表vo
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class ShopManagerListVO implements Serializable{

    private static final long serialVersionUID = 2244707275972212922L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "时间")
    private Date gmtCreate;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
