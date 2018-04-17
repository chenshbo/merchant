package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SupplierQueryReq implements Serializable{

    private static final long serialVersionUID = -5736802827734888691L;

    @ApiModelProperty(value = "手机号码")
    private String mobileNo;

    @ApiModelProperty(value = "审核状态，0-未审核1-审核通过2-审核驳回")
    private Integer status;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}