package com.jiangzuoyoupin.dto;

import java.util.Date;

/**
 * 功能模块: 匠探dto
 *
 * @author chenshangbo
 * @date 2018-05-05 11:03:29
 */
public class ShopMangerDto {

    private Long id;
    private String nickName;
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
