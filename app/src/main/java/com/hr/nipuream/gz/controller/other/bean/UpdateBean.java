package com.hr.nipuream.gz.controller.other.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-24 12:18
 * 邮箱：571829491@qq.com
 */
public class UpdateBean implements Serializable{

    private String message;
    private String createTime;
    private String upgradecontent;
    private String uploadFile;
    private int status;
    private String typeid;
    private String versionIndex;
    private String versionId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpgradecontent() {
        return upgradecontent;
    }

    public void setUpgradecontent(String upgradecontent) {
        this.upgradecontent = upgradecontent;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getVersionIndex() {
        return versionIndex;
    }

    public void setVersionIndex(String versionIndex) {
        this.versionIndex = versionIndex;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "message='" + message + '\'' +
                ", createTime='" + createTime + '\'' +
                ", upgradecontent='" + upgradecontent + '\'' +
                ", uploadFile='" + uploadFile + '\'' +
                ", status=" + status +
                ", typeid='" + typeid + '\'' +
                ", versionIndex='" + versionIndex + '\'' +
                ", versionId='" + versionId + '\'' +
                '}';
    }
}
