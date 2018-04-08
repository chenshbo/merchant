package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 下拉框列表vo
 *
 * @author chenshangbo
 * @date 2017-12-15 14:15:25
 */
public class SelectVO implements Serializable{

	private static final long serialVersionUID = -1061320614972742457L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "名称")
	private String name;

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
}
