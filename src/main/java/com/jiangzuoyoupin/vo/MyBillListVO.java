package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 我的消费列表
 *
 * @author chenshangbo
 * @date 2018-05-06 16:42:57
 */
public class MyBillListVO implements Serializable{

    private static final long serialVersionUID = 5641481862564570882L;

    @ApiModelProperty(value = "账单id")
    private Long id;

    @ApiModelProperty(value = "消费金额")
    private Double amount;

    @ApiModelProperty(value = "状态，0待审核1审核通过2转账3提现4完成")
    private Integer status;

    @ApiModelProperty(value = "排序状态，0待位1达标2完成")
    private Integer sortStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortStatus() {
        return sortStatus;
    }

    public void setSortStatus(Integer sortStatus) {
        this.sortStatus = sortStatus;
    }
}
