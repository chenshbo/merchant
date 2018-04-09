package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: ID查询请求参数
 *
 * @author chenshangbo
 * @date 2017-11-7 15:21:48
 */
public class ProvinceIdReq implements Serializable {

    private static final long serialVersionUID = 608712927820660766L;

    @ApiModelProperty(value = "省份id",required = true)
    private Long provinceId;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
}
