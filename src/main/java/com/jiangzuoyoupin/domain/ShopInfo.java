package com.jiangzuoyoupin.domain;

import java.util.Date;

public class ShopInfo {
    private Long id;

    private Long shopownerId;

    private String shopName;

    private String shopSuffixName;

    private String telephone;

    private String address;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}