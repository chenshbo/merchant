package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 店铺保存req
 *
 * @author chenshangbo
 * @date 2018-04-24 23:22:54
 */
public class ShopInfoSaveReq implements Serializable{

    private static final long serialVersionUID = 6768218712375022287L;

    @ApiModelProperty(value = "店主id")
    private Long shopownerId;

    @ApiModelProperty(value = "店名")
    private String shopName;

    @ApiModelProperty(value = "后缀")
    private String shopSuffixName;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "地址")
    private String address;

    public Long getShopownerId() {
        return shopownerId;
    }

    public void setShopownerId(Long shopownerId) {
        this.shopownerId = shopownerId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopSuffixName() {
        return shopSuffixName;
    }

    public void setShopSuffixName(String shopSuffixName) {
        this.shopSuffixName = shopSuffixName == null ? null : shopSuffixName.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

}