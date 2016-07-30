package com.hr.nipuream.gz.controller.other.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-21 21:02
 * 邮箱：571829491@qq.com
 */
public class LoginBean implements Serializable{

    /**
     * 0 为请求成功
     */
    private int status;

    private String message;

    /**
     * 登录状态 1 成功 2 失败
     */
    private int su;

    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSu() {
        return su;
    }

    public void setSu(int su) {
        this.su = su;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", su=" + su +
                ", userid=" + userid +
                '}';
    }
}
