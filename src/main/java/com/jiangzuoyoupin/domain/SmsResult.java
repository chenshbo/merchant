package com.jiangzuoyoupin.domain;

/**
 * 功能描述: 短信返回结果
 *
 * @author: chenshangbo
 * @date: 2018/4/9 16:42
 */
public class SmsResult {

    /** 响应时间戳 */
    private String ts;
    /** 状态（0表示成功，其他表示失败） */
    private int result;
    /** 消息id */
    private String msgid;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
