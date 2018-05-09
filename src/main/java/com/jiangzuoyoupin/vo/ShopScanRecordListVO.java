package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ShopScanRecordListVO implements Serializable{

    private static final long serialVersionUID = 2464959584984779772L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "收藏状态 0未收藏1已收藏")
    private Integer collectStatus;

    @ApiModelProperty(value = "店名")
    private String shopName;

    @ApiModelProperty(value = "店名后缀")
    private String shopSuffixName;

    @ApiModelProperty(value = "店铺联系电话")
    private String telephone;

    @ApiModelProperty(value = "店铺联系地址")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopSuffixName() {
        return shopSuffixName;
    }

    public void setShopSuffixName(String shopSuffixName) {
        this.shopSuffixName = shopSuffixName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
