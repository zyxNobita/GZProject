package com.hr.nipuream.gz.controller.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import java.io.Serializable;

/**
 * 描述：国资详情
 * 作者：Nipuream
 * 时间: 2016-07-23 19:30
 * 邮箱：571829491@qq.com
 */
@Entity(nameInDb = "SAC_INFO")
public class GZbean implements Serializable{

    private String accounttype;
    private String approvalnumber;

    @NotNull
    private String createtime;
    private String deptname;
    private String entrydate;
    private String etime;

    @NotNull
    private String factorynumber;
    private String fadate;
    private String faopinion;
    private String faperson;

    @NotNull
    private int fastaus;
    private String financialclass;
    private String fundssource;

    @Id(autoincrement = true)
    @NotNull
    private long id;
    private String lat;
    private String lng;
    private String macname;

    @NotNull
    private String mid;

    @NotNull
    private String nowstatus;
    private String organization;
    private String projectnumber;
    private String purchasedate;
    private String qrphoto;
    private String remark;
    private String sacmodel;

    @NotNull
    private String sacname;

    @NotNull
    private String sacno;

    @NotNull
    private int sacnumber;
    private String sacphoto;
    private String sacsource;
    private String sacspec;

    @NotNull
    private int sacstatus;
    private String savepath;
    private String stime;

    @NotNull
    private String storagelocation;
    private String subject;
    private String subjecttype;
    private String techcompany;
    private String techdate;
    private String techopinion;
    private String techperson;

    @NotNull
    private int  techstaus;

    @NotNull
    private int totalprice;
    private String transferdate;

    @NotNull
    private int typeid;
    private String typename;
    private String unit;

    @NotNull
    private int unitprice;

    @NotNull
    private int usedeptid;
    private String usedirection;
    private String usepeople;
    private String userid;
    private String username;
    private String voucherno;


    @Generated(hash = 788678774)
    public GZbean(String accounttype, String approvalnumber,
            @NotNull String createtime, String deptname, String entrydate,
            String etime, @NotNull String factorynumber, String fadate,
            String faopinion, String faperson, int fastaus, String financialclass,
            String fundssource, long id, String lat, String lng, String macname,
            @NotNull String mid, @NotNull String nowstatus, String organization,
            String projectnumber, String purchasedate, String qrphoto,
            String remark, String sacmodel, @NotNull String sacname,
            @NotNull String sacno, int sacnumber, String sacphoto,
            String sacsource, String sacspec, int sacstatus, String savepath,
            String stime, @NotNull String storagelocation, String subject,
            String subjecttype, String techcompany, String techdate,
            String techopinion, String techperson, int techstaus, int totalprice,
            String transferdate, int typeid, String typename, String unit,
            int unitprice, int usedeptid, String usedirection, String usepeople,
            String userid, String username, String voucherno) {
        this.accounttype = accounttype;
        this.approvalnumber = approvalnumber;
        this.createtime = createtime;
        this.deptname = deptname;
        this.entrydate = entrydate;
        this.etime = etime;
        this.factorynumber = factorynumber;
        this.fadate = fadate;
        this.faopinion = faopinion;
        this.faperson = faperson;
        this.fastaus = fastaus;
        this.financialclass = financialclass;
        this.fundssource = fundssource;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.macname = macname;
        this.mid = mid;
        this.nowstatus = nowstatus;
        this.organization = organization;
        this.projectnumber = projectnumber;
        this.purchasedate = purchasedate;
        this.qrphoto = qrphoto;
        this.remark = remark;
        this.sacmodel = sacmodel;
        this.sacname = sacname;
        this.sacno = sacno;
        this.sacnumber = sacnumber;
        this.sacphoto = sacphoto;
        this.sacsource = sacsource;
        this.sacspec = sacspec;
        this.sacstatus = sacstatus;
        this.savepath = savepath;
        this.stime = stime;
        this.storagelocation = storagelocation;
        this.subject = subject;
        this.subjecttype = subjecttype;
        this.techcompany = techcompany;
        this.techdate = techdate;
        this.techopinion = techopinion;
        this.techperson = techperson;
        this.techstaus = techstaus;
        this.totalprice = totalprice;
        this.transferdate = transferdate;
        this.typeid = typeid;
        this.typename = typename;
        this.unit = unit;
        this.unitprice = unitprice;
        this.usedeptid = usedeptid;
        this.usedirection = usedirection;
        this.usepeople = usepeople;
        this.userid = userid;
        this.username = username;
        this.voucherno = voucherno;
    }

    @Generated(hash = 1845992144)
    public GZbean() {
    }


    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getApprovalnumber() {
        return approvalnumber;
    }

    public void setApprovalnumber(String approvalnumber) {
        this.approvalnumber = approvalnumber;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getFactorynumber() {
        return factorynumber;
    }

    public void setFactorynumber(String factorynumber) {
        this.factorynumber = factorynumber;
    }

    public String getFadate() {
        return fadate;
    }

    public void setFadate(String fadate) {
        this.fadate = fadate;
    }

    public String getFaopinion() {
        return faopinion;
    }

    public void setFaopinion(String faopinion) {
        this.faopinion = faopinion;
    }

    public String getFaperson() {
        return faperson;
    }

    public void setFaperson(String faperson) {
        this.faperson = faperson;
    }

    public int getFastaus() {
        return fastaus;
    }

    public void setFastaus(int fastaus) {
        this.fastaus = fastaus;
    }

    public String getFinancialclass() {
        return financialclass;
    }

    public void setFinancialclass(String financialclass) {
        this.financialclass = financialclass;
    }

    public String getFundssource() {
        return fundssource;
    }

    public void setFundssource(String fundssource) {
        this.fundssource = fundssource;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMacname() {
        return macname;
    }

    public void setMacname(String macname) {
        this.macname = macname;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getNowstatus() {
        return nowstatus;
    }

    public void setNowstatus(String nowstatus) {
        this.nowstatus = nowstatus;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProjectnumber() {
        return projectnumber;
    }

    public void setProjectnumber(String projectnumber) {
        this.projectnumber = projectnumber;
    }

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getQrphoto() {
        return qrphoto;
    }

    public void setQrphoto(String qrphoto) {
        this.qrphoto = qrphoto;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSacmodel() {
        return sacmodel;
    }

    public void setSacmodel(String sacmodel) {
        this.sacmodel = sacmodel;
    }

    public String getSacname() {
        return sacname;
    }

    public void setSacname(String sacname) {
        this.sacname = sacname;
    }

    public String getSacno() {
        return sacno;
    }

    public void setSacno(String sacno) {
        this.sacno = sacno;
    }

    public int getSacnumber() {
        return sacnumber;
    }

    public void setSacnumber(int sacnumber) {
        this.sacnumber = sacnumber;
    }

    public String getSacphoto() {
        return sacphoto;
    }

    public void setSacphoto(String sacphoto) {
        this.sacphoto = sacphoto;
    }

    public String getSacsource() {
        return sacsource;
    }

    public void setSacsource(String sacsource) {
        this.sacsource = sacsource;
    }

    public String getSacspec() {
        return sacspec;
    }

    public void setSacspec(String sacspec) {
        this.sacspec = sacspec;
    }

    public int getSacstatus() {
        return sacstatus;
    }

    public void setSacstatus(int sacstatus) {
        this.sacstatus = sacstatus;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getStoragelocation() {
        return storagelocation;
    }

    public void setStoragelocation(String storagelocation) {
        this.storagelocation = storagelocation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjecttype() {
        return subjecttype;
    }

    public void setSubjecttype(String subjecttype) {
        this.subjecttype = subjecttype;
    }

    public String getTechcompany() {
        return techcompany;
    }

    public void setTechcompany(String techcompany) {
        this.techcompany = techcompany;
    }

    public String getTechdate() {
        return techdate;
    }

    public void setTechdate(String techdate) {
        this.techdate = techdate;
    }

    public String getTechopinion() {
        return techopinion;
    }

    public void setTechopinion(String techopinion) {
        this.techopinion = techopinion;
    }

    public String getTechperson() {
        return techperson;
    }

    public void setTechperson(String techperson) {
        this.techperson = techperson;
    }

    public int getTechstaus() {
        return techstaus;
    }

    public void setTechstaus(int techstaus) {
        this.techstaus = techstaus;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public String getTransferdate() {
        return transferdate;
    }

    public void setTransferdate(String transferdate) {
        this.transferdate = transferdate;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public int getUsedeptid() {
        return usedeptid;
    }

    public void setUsedeptid(int usedeptid) {
        this.usedeptid = usedeptid;
    }

    public String getUsedirection() {
        return usedirection;
    }

    public void setUsedirection(String usedirection) {
        this.usedirection = usedirection;
    }

    public String getUsepeople() {
        return usepeople;
    }

    public void setUsepeople(String usepeople) {
        this.usepeople = usepeople;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVoucherno() {
        return voucherno;
    }

    public void setVoucherno(String voucherno) {
        this.voucherno = voucherno;
    }

    @Override
    public String toString() {
        return "GZbean{" +
                "accounttype='" + accounttype + '\'' +
                ", approvalnumber='" + approvalnumber + '\'' +
                ", createtime='" + createtime + '\'' +
                ", deptname='" + deptname + '\'' +
                ", entrydate='" + entrydate + '\'' +
                ", etime='" + etime + '\'' +
                ", factorynumber='" + factorynumber + '\'' +
                ", fadate='" + fadate + '\'' +
                ", faopinion='" + faopinion + '\'' +
                ", faperson='" + faperson + '\'' +
                ", fastaus=" + fastaus +
                ", financialclass='" + financialclass + '\'' +
                ", fundssource='" + fundssource + '\'' +
                ", id=" + id +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", macname='" + macname + '\'' +
                ", mid='" + mid + '\'' +
                ", nowstatus='" + nowstatus + '\'' +
                ", organization='" + organization + '\'' +
                ", projectnumber='" + projectnumber + '\'' +
                ", purchasedate='" + purchasedate + '\'' +
                ", qrphoto='" + qrphoto + '\'' +
                ", remark='" + remark + '\'' +
                ", sacmodel='" + sacmodel + '\'' +
                ", sacname='" + sacname + '\'' +
                ", sacno='" + sacno + '\'' +
                ", sacnumber=" + sacnumber +
                ", sacphoto='" + sacphoto + '\'' +
                ", sacsource='" + sacsource + '\'' +
                ", sacspec='" + sacspec + '\'' +
                ", sacstatus=" + sacstatus +
                ", savepath='" + savepath + '\'' +
                ", stime='" + stime + '\'' +
                ", storagelocation='" + storagelocation + '\'' +
                ", subject='" + subject + '\'' +
                ", subjecttype='" + subjecttype + '\'' +
                ", techcompany='" + techcompany + '\'' +
                ", techdate='" + techdate + '\'' +
                ", techopinion='" + techopinion + '\'' +
                ", techperson='" + techperson + '\'' +
                ", techstaus=" + techstaus +
                ", totalprice=" + totalprice +
                ", transferdate='" + transferdate + '\'' +
                ", typeid=" + typeid +
                ", typename='" + typename + '\'' +
                ", unit='" + unit + '\'' +
                ", unitprice=" + unitprice +
                ", usedeptid=" + usedeptid +
                ", usedirection='" + usedirection + '\'' +
                ", usepeople='" + usepeople + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", voucherno='" + voucherno + '\'' +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
