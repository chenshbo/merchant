package com.jiangzuoyoupin.dto;

import com.jiangzuoyoupin.domain.ShopScanRecord;

/**
 * Created by Administrator on 2018/5/9.
 */
public class ShopScanRecordDto extends ShopScanRecord{

    private String mobileNo;

    private String shopName;

    private String shopSuffixName;

    private String telephone;

    private String address;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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
