package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 区域树vo
 *
 * @author chenshangbo
 * @date 2017-12-15 14:15:25
 */
public class AreaTreeVO implements Serializable {

    private static final long serialVersionUID = 8479685745639248310L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "子元素")
    private List<AreaTreeVO> subList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<AreaTreeVO> getSubList() {
        return subList;
    }

    public void setSubList(List<AreaTreeVO> subList) {
        this.subList = subList;
    }
}
