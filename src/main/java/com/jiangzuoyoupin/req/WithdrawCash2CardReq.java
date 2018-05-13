package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 提现到银行卡req
 *
 * @author chenshangbo
 * @date 2018-05-13 20:49:34
 */
public class WithdrawCash2CardReq implements Serializable {

    private static final long serialVersionUID = -4700485972765089887L;

    @ApiModelProperty(value = "用户id",required = true)
    private Long weChatUserId;
    @ApiModelProperty(value = "账单id",required = true)
    private Long shopBillId;
    @ApiModelProperty(value = "收款方银行账号",required = true)
    private String encBankNo;
    @ApiModelProperty(value = "收款方用户名",required = true)
    private String encTrueName;
    @ApiModelProperty(value = "收款方开户行编码",required = true)
    private String bankCode;

    public Long getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(Long weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public String getEncBankNo() {
        return encBankNo;
    }

    public void setEncBankNo(String encBankNo) {
        this.encBankNo = encBankNo;
    }

    public String getEncTrueName() {
        return encTrueName;
    }

    public void setEncTrueName(String encTrueName) {
        this.encTrueName = encTrueName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getShopBillId() {
        return shopBillId;
    }

    public void setShopBillId(Long shopBillId) {
        this.shopBillId = shopBillId;
    }
}
