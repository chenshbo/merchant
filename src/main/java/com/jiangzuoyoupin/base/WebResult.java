package com.jiangzuoyoupin.base;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description: 返回结果对象
 * @author 刘恩
 * @date 2017-11-06 14:17:25
 */
public class WebResult<T> implements Serializable{

	private static final long serialVersionUID = -4912299918704436463L;

	@ApiModelProperty(value = "错误码")
	private int errCode;
	
	@ApiModelProperty(value = "错误信息")
	private String errMsg;
	
	/** 服务端实际返回的数据*/
	@ApiModelProperty(value = "业务数据")
	private T data;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
