package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 功能模块: 供应商审核req
 *
 * @author chenshangbo
 * @date 2018-04-17 22:47:27
 */
public class SupplierCheckReq implements Serializable{

    private static final long serialVersionUID = -8989402901909710572L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "审核状态，0-未审核1-审核通过2-审核驳回")
    private Integer status;

    @ApiModelProperty(value = "审核意见")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}