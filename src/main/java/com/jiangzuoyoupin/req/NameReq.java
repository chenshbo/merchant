package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 名称查询请求参数
 *
 * @author chenshangbo
 * @date 2018-1-10 09:32:05
 */
public class NameReq implements Serializable {

    private static final long serialVersionUID = -5152847442526414838L;

    @ApiModelProperty(value = "名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
