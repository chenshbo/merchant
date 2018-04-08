package com.jiangzuoyoupin.base;

import io.swagger.annotations.ApiModelProperty;

/**
 * Description: 分页返回对象
 * @author 陈尚波
 * @date 2018-4-8 22:21:40
 */
public class WebPageResult<T> extends WebResult<T>{

	private static final long serialVersionUID = 176135191864856865L;

	@ApiModelProperty(value = "总页数")
	private Integer totalPage;

	@ApiModelProperty(value = "总数据量")
	private Long totalRecords;

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
