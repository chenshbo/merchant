package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 主键ID查询请求参数
 *
 * @author chenshangbo
 * @date 2017-11-7 15:21:48
 */
public class IdReq implements Serializable {

    private static final long serialVersionUID = 8427102945616948844L;

    @ApiModelProperty(value = "主键id",required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
