package com.jiangzuoyoupin.base;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 分页请求参数对象
 * @author 陈尚波
 * @date 2017-11-06 14:08:33
 */
public class PageReq implements Serializable{

	private static final long serialVersionUID = 8224996521395596800L;
	/** 当前页码 */
	@ApiModelProperty(value = "当前页码")
	private Integer currPage = 1;
	/** 当前页显示的数量 */
	@ApiModelProperty(value = "当前页显示的数量")
	private Integer pageRows = 10;
	/** 是否需要分页 默认不需要  */
	@ApiModelProperty(value = "是否需要分页")
	private Boolean pagingRequired = false;

	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getPageRows() {
		return pageRows;
	}
	public void setPageRows(Integer pageRows) {
		this.pageRows = pageRows;
	}
	public Boolean getPagingRequired() {
		return pagingRequired;
	}
	public void setPagingRequired(Boolean pagingRequired) {
		this.pagingRequired = pagingRequired;
	}
}
