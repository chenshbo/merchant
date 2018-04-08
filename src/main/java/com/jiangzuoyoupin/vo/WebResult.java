package com.jiangzuoyoupin.vo;

/**
 * Created by Administrator on 2018/4/7.
 */
public class WebResult<T> {

    private int errCode;
    private String errMsg;
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

    public static WebResult success(Object data){
        WebResult result = new WebResult();
        result.setErrCode(0);
        result.setErrMsg("成功");
        result.setData(data);
        return result;
    }
}
