package com.jiangzuoyoupin.req;

import com.jiangzuoyoupin.base.PageReq;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 用户ID分页查询请求参数
 *
 * @author chenshangbo
 * @date 2017-11-7 15:21:48
 */
public class ScanRecordQueryReq extends PageReq {

    private static final long serialVersionUID = 2171412664160942204L;

    @ApiModelProperty(value = "用户id",required = true)
    private Long weChatUserId;

    @ApiModelProperty(value = "收藏状态，查询浏览记录不传，查询收藏记录传1")
    private Integer collectStatus;

    public Long getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(Long weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}
