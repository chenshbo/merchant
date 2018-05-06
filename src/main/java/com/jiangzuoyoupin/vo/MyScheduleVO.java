package com.jiangzuoyoupin.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 功能模块: 我的免单进度vo
 *
 * @author chenshangbo
 * @date 2018-05-06 16:42:57
 */
public class MyScheduleVO implements Serializable{

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "进度")
    private String schedule;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
