package com.hr.nipuream.gz.controller.main.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-28 20:21
 * 邮箱：571829491@qq.com
 */
public class GZRepairbean implements Serializable{

    private long id ;

    /**
     * 外键 国资信息表
     */
    private int sacid;

    /**
     * 用户ID
     */
    private int userid;

    /**
     * 维修时间
     */
    private String  maintaintime;

    /**
     * 维修人
     */
    private String maintainman;

    /**
     * 国资名称
     */
    private String sacname;

    /**
     * 问题描述
     */
    private String problemdesc;

    /**
     * 预计维修完成时间
     */
    private String  ectime;

    /**
     * 创建时间
     */
    private String  createtime;

    /**
     * 维修状态
     * 1 维修中 2 维修完成  默认维修中
     */
    private int staus;

    /**
     * 维修完成时间
     */
    private String completiontime;

    public String getSacname() {
        return sacname;
    }

    public void setSacname(String sacname) {
        this.sacname = sacname;
    }

    /**
     * 完成备注
     */
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSacid() {
        return sacid;
    }

    public void setSacid(int sacid) {
        this.sacid = sacid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String  getMaintaintime() {
        return maintaintime;
    }

    public void setMaintaintime(String maintaintime) {
        this.maintaintime = maintaintime;
    }

    public String getMaintainman() {
        return maintainman;
    }

    public void setMaintainman(String maintainman) {
        this.maintainman = maintainman;
    }

    public String getProblemdesc() {
        return problemdesc;
    }

    public void setProblemdesc(String problemdesc) {
        this.problemdesc = problemdesc;
    }

    public String getEctime() {
        return ectime;
    }

    public void setEctime(String ectime) {
        this.ectime = ectime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public String getCompletiontime() {
        return completiontime;
    }

    public void setCompletiontime(String completiontime) {
        this.completiontime = completiontime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GZRepairbean{" +
                "id=" + id +
                ", sacid=" + sacid +
                ", userid=" + userid +
                ", maintaintime=" + maintaintime +
                ", maintainman='" + maintainman + '\'' +
                ", problemdesc='" + problemdesc + '\'' +
                ", ectime=" + ectime +
                ", createtime=" + createtime +
                ", staus=" + staus +
                ", completiontime=" + completiontime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
