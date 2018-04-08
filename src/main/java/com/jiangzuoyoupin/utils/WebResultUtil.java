package com.jiangzuoyoupin.utils;

import com.github.pagehelper.Page;
import com.jiangzuoyoupin.base.WebPageResult;
import com.jiangzuoyoupin.base.WebResult;

import java.util.List;

public class WebResultUtil {

    public static WebResult returnResult(Object data) {
        WebResult webResult = returnResult();
        webResult.setData(data);
        return webResult;
    }

    public static WebResult returnResult() {
        WebResult entity = new WebResult();
        entity.setErrCode(0);
        entity.setErrMsg("成功");
        return entity;
    }

    public static WebResult returnErrMsgResult(String errMsg) {
        WebResult entity = new WebResult();
        entity.setErrCode(-1);
        entity.setErrMsg(errMsg);
        return entity;
    }

    public static WebPageResult returnPageResult(Object data, Boolean pagingRequired) {
        return returnPageResult(data,null,pagingRequired);
    }

    public static WebPageResult returnPageResult(Object data, Object pageData, Boolean pagingRequired) {
        WebPageResult entity = new WebPageResult();
        entity.setErrCode(0);
        entity.setErrMsg("成功");
        entity.setData(data);
        if (pagingRequired != null && pagingRequired && !isEmpty(data)) {
            Page page;
            if(pageData != null){
                page = (Page) pageData;
            }else{
                page = (Page) data;
            }
            entity.setTotalRecords(page.getTotal());
            entity.setTotalPage(page.getPages());
            return entity;
        }
        return entity;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return ((List) obj).isEmpty();
        }
        if ((obj instanceof String)) {
            return ("").equals(((String) obj).trim());
        }
        return false;
    }

}
