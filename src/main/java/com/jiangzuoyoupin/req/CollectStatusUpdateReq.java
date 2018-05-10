package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 收藏状态更新请求req
 *
 * @author chenshangbo
 * @date 2017-11-7 15:21:48
 */
public class CollectStatusUpdateReq implements Serializable {

    private static final long serialVersionUID = 1034908132766037845L;

    @ApiModelProperty(value = "主键id",required = true)
    private Long id;

    @ApiModelProperty(value = "收藏状态 0取消收藏1收藏",required = true)
    private Integer collectStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}
