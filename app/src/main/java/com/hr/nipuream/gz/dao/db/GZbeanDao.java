package com.hr.nipuream.gz.dao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hr.nipuream.gz.controller.main.bean.GZbean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SAC_INFO".
*/
public class GZbeanDao extends AbstractDao<GZbean, Long> {

    public static final String TABLENAME = "SAC_INFO";

    /**
     * Properties of entity GZbean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Accounttype = new Property(0, String.class, "accounttype", false, "ACCOUNTTYPE");
        public final static Property Approvalnumber = new Property(1, String.class, "approvalnumber", false, "APPROVALNUMBER");
        public final static Property Createtime = new Property(2, String.class, "createtime", false, "CREATETIME");
        public final static Property Deptname = new Property(3, String.class, "deptname", false, "DEPTNAME");
        public final static Property Entrydate = new Property(4, String.class, "entrydate", false, "ENTRYDATE");
        public final static Property Etime = new Property(5, String.class, "etime", false, "ETIME");
        public final static Property Factorynumber = new Property(6, String.class, "factorynumber", false, "FACTORYNUMBER");
        public final static Property Fadate = new Property(7, String.class, "fadate", false, "FADATE");
        public final static Property Faopinion = new Property(8, String.class, "faopinion", false, "FAOPINION");
        public final static Property Faperson = new Property(9, String.class, "faperson", false, "FAPERSON");
        public final static Property Fastaus = new Property(10, int.class, "fastaus", false, "FASTAUS");
        public final static Property Financialclass = new Property(11, String.class, "financialclass", false, "FINANCIALCLASS");
        public final static Property Fundssource = new Property(12, String.class, "fundssource", false, "FUNDSSOURCE");
        public final static Property Id = new Property(13, long.class, "id", true, "_id");
        public final static Property Lat = new Property(14, String.class, "lat", false, "LAT");
        public final static Property Lng = new Property(15, String.class, "lng", false, "LNG");
        public final static Property Macname = new Property(16, String.class, "macname", false, "MACNAME");
        public final static Property Mid = new Property(17, String.class, "mid", false, "MID");
        public final static Property Nowstatus = new Property(18, String.class, "nowstatus", false, "NOWSTATUS");
        public final static Property Organization = new Property(19, String.class, "organization", false, "ORGANIZATION");
        public final static Property Projectnumber = new Property(20, String.class, "projectnumber", false, "PROJECTNUMBER");
        public final static Property Purchasedate = new Property(21, String.class, "purchasedate", false, "PURCHASEDATE");
        public final static Property Qrphoto = new Property(22, String.class, "qrphoto", false, "QRPHOTO");
        public final static Property Remark = new Property(23, String.class, "remark", false, "REMARK");
        public final static Property Sacmodel = new Property(24, String.class, "sacmodel", false, "SACMODEL");
        public final static Property Sacname = new Property(25, String.class, "sacname", false, "SACNAME");
        public final static Property Sacno = new Property(26, String.class, "sacno", false, "SACNO");
        public final static Property Sacnumber = new Property(27, int.class, "sacnumber", false, "SACNUMBER");
        public final static Property Sacphoto = new Property(28, String.class, "sacphoto", false, "SACPHOTO");
        public final static Property Sacsource = new Property(29, String.class, "sacsource", false, "SACSOURCE");
        public final static Property Sacspec = new Property(30, String.class, "sacspec", false, "SACSPEC");
        public final static Property Sacstatus = new Property(31, int.class, "sacstatus", false, "SACSTATUS");
        public final static Property Savepath = new Property(32, String.class, "savepath", false, "SAVEPATH");
        public final static Property Stime = new Property(33, String.class, "stime", false, "STIME");
        public final static Property Storagelocation = new Property(34, String.class, "storagelocation", false, "STORAGELOCATION");
        public final static Property Subject = new Property(35, String.class, "subject", false, "SUBJECT");
        public final static Property Subjecttype = new Property(36, String.class, "subjecttype", false, "SUBJECTTYPE");
        public final static Property Techcompany = new Property(37, String.class, "techcompany", false, "TECHCOMPANY");
        public final static Property Techdate = new Property(38, String.class, "techdate", false, "TECHDATE");
        public final static Property Techopinion = new Property(39, String.class, "techopinion", false, "TECHOPINION");
        public final static Property Techperson = new Property(40, String.class, "techperson", false, "TECHPERSON");
        public final static Property Techstaus = new Property(41, int.class, "techstaus", false, "TECHSTAUS");
        public final static Property Totalprice = new Property(42, int.class, "totalprice", false, "TOTALPRICE");
        public final static Property Transferdate = new Property(43, String.class, "transferdate", false, "TRANSFERDATE");
        public final static Property Typeid = new Property(44, int.class, "typeid", false, "TYPEID");
        public final static Property Typename = new Property(45, String.class, "typename", false, "TYPENAME");
        public final static Property Unit = new Property(46, String.class, "unit", false, "UNIT");
        public final static Property Unitprice = new Property(47, int.class, "unitprice", false, "UNITPRICE");
        public final static Property Usedeptid = new Property(48, int.class, "usedeptid", false, "USEDEPTID");
        public final static Property Usedirection = new Property(49, String.class, "usedirection", false, "USEDIRECTION");
        public final static Property Usepeople = new Property(50, String.class, "usepeople", false, "USEPEOPLE");
        public final static Property Userid = new Property(51, String.class, "userid", false, "USERID");
        public final static Property Username = new Property(52, String.class, "username", false, "USERNAME");
        public final static Property Voucherno = new Property(53, String.class, "voucherno", false, "VOUCHERNO");
    };


    public GZbeanDao(DaoConfig config) {
        super(config);
    }
    
    public GZbeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SAC_INFO\" (" + //
                "\"ACCOUNTTYPE\" TEXT," + // 0: accounttype
                "\"APPROVALNUMBER\" TEXT," + // 1: approvalnumber
                "\"CREATETIME\" TEXT NOT NULL ," + // 2: createtime
                "\"DEPTNAME\" TEXT," + // 3: deptname
                "\"ENTRYDATE\" TEXT," + // 4: entrydate
                "\"ETIME\" TEXT," + // 5: etime
                "\"FACTORYNUMBER\" TEXT," + // 6: factorynumber
                "\"FADATE\" TEXT," + // 7: fadate
                "\"FAOPINION\" TEXT," + // 8: faopinion
                "\"FAPERSON\" TEXT," + // 9: faperson
                "\"FASTAUS\" INTEGER NOT NULL ," + // 10: fastaus
                "\"FINANCIALCLASS\" TEXT," + // 11: financialclass
                "\"FUNDSSOURCE\" TEXT," + // 12: fundssource
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 13: id
                "\"LAT\" TEXT," + // 14: lat
                "\"LNG\" TEXT," + // 15: lng
                "\"MACNAME\" TEXT," + // 16: macname
                "\"MID\" TEXT NOT NULL ," + // 17: mid
                "\"NOWSTATUS\" TEXT NOT NULL ," + // 18: nowstatus
                "\"ORGANIZATION\" TEXT," + // 19: organization
                "\"PROJECTNUMBER\" TEXT," + // 20: projectnumber
                "\"PURCHASEDATE\" TEXT," + // 21: purchasedate
                "\"QRPHOTO\" TEXT," + // 22: qrphoto
                "\"REMARK\" TEXT," + // 23: remark
                "\"SACMODEL\" TEXT," + // 24: sacmodel
                "\"SACNAME\" TEXT NOT NULL ," + // 25: sacname
                "\"SACNO\" TEXT NOT NULL ," + // 26: sacno
                "\"SACNUMBER\" INTEGER NOT NULL ," + // 27: sacnumber
                "\"SACPHOTO\" TEXT," + // 28: sacphoto
                "\"SACSOURCE\" TEXT," + // 29: sacsource
                "\"SACSPEC\" TEXT," + // 30: sacspec
                "\"SACSTATUS\" INTEGER NOT NULL ," + // 31: sacstatus
                "\"SAVEPATH\" TEXT," + // 32: savepath
                "\"STIME\" TEXT," + // 33: stime
                "\"STORAGELOCATION\" TEXT NOT NULL ," + // 34: storagelocation
                "\"SUBJECT\" TEXT," + // 35: subject
                "\"SUBJECTTYPE\" TEXT," + // 36: subjecttype
                "\"TECHCOMPANY\" TEXT," + // 37: techcompany
                "\"TECHDATE\" TEXT," + // 38: techdate
                "\"TECHOPINION\" TEXT," + // 39: techopinion
                "\"TECHPERSON\" TEXT," + // 40: techperson
                "\"TECHSTAUS\" INTEGER NOT NULL ," + // 41: techstaus
                "\"TOTALPRICE\" INTEGER NOT NULL ," + // 42: totalprice
                "\"TRANSFERDATE\" TEXT," + // 43: transferdate
                "\"TYPEID\" INTEGER NOT NULL ," + // 44: typeid
                "\"TYPENAME\" TEXT," + // 45: typename
                "\"UNIT\" TEXT," + // 46: unit
                "\"UNITPRICE\" INTEGER NOT NULL ," + // 47: unitprice
                "\"USEDEPTID\" INTEGER NOT NULL ," + // 48: usedeptid
                "\"USEDIRECTION\" TEXT," + // 49: usedirection
                "\"USEPEOPLE\" TEXT," + // 50: usepeople
                "\"USERID\" TEXT," + // 51: userid
                "\"USERNAME\" TEXT," + // 52: username
                "\"VOUCHERNO\" TEXT);"); // 53: voucherno
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SAC_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GZbean entity) {
        stmt.clearBindings();
 
        String accounttype = entity.getAccounttype();
        if (accounttype != null) {
            stmt.bindString(1, accounttype);
        }
 
        String approvalnumber = entity.getApprovalnumber();
        if (approvalnumber != null) {
            stmt.bindString(2, approvalnumber);
        }
        stmt.bindString(3, entity.getCreatetime());
 
        String deptname = entity.getDeptname();
        if (deptname != null) {
            stmt.bindString(4, deptname);
        }
 
        String entrydate = entity.getEntrydate();
        if (entrydate != null) {
            stmt.bindString(5, entrydate);
        }
 
        String etime = entity.getEtime();
        if (etime != null) {
            stmt.bindString(6, etime);
        }
 
        String factorynumber = entity.getFactorynumber();
        if (factorynumber != null) {
            stmt.bindString(7, factorynumber);
        }
 
        String fadate = entity.getFadate();
        if (fadate != null) {
            stmt.bindString(8, fadate);
        }
 
        String faopinion = entity.getFaopinion();
        if (faopinion != null) {
            stmt.bindString(9, faopinion);
        }
 
        String faperson = entity.getFaperson();
        if (faperson != null) {
            stmt.bindString(10, faperson);
        }
        stmt.bindLong(11, entity.getFastaus());
 
        String financialclass = entity.getFinancialclass();
        if (financialclass != null) {
            stmt.bindString(12, financialclass);
        }
 
        String fundssource = entity.getFundssource();
        if (fundssource != null) {
            stmt.bindString(13, fundssource);
        }
        stmt.bindLong(14, entity.getId());
 
        String lat = entity.getLat();
        if (lat != null) {
            stmt.bindString(15, lat);
        }
 
        String lng = entity.getLng();
        if (lng != null) {
            stmt.bindString(16, lng);
        }
 
        String macname = entity.getMacname();
        if (macname != null) {
            stmt.bindString(17, macname);
        }
        stmt.bindString(18, entity.getMid());
        stmt.bindString(19, entity.getNowstatus());
 
        String organization = entity.getOrganization();
        if (organization != null) {
            stmt.bindString(20, organization);
        }
 
        String projectnumber = entity.getProjectnumber();
        if (projectnumber != null) {
            stmt.bindString(21, projectnumber);
        }
 
        String purchasedate = entity.getPurchasedate();
        if (purchasedate != null) {
            stmt.bindString(22, purchasedate);
        }
 
        String qrphoto = entity.getQrphoto();
        if (qrphoto != null) {
            stmt.bindString(23, qrphoto);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(24, remark);
        }
 
        String sacmodel = entity.getSacmodel();
        if (sacmodel != null) {
            stmt.bindString(25, sacmodel);
        }
        stmt.bindString(26, entity.getSacname());
        stmt.bindString(27, entity.getSacno());
        stmt.bindLong(28, entity.getSacnumber());
 
        String sacphoto = entity.getSacphoto();
        if (sacphoto != null) {
            stmt.bindString(29, sacphoto);
        }
 
        String sacsource = entity.getSacsource();
        if (sacsource != null) {
            stmt.bindString(30, sacsource);
        }
 
        String sacspec = entity.getSacspec();
        if (sacspec != null) {
            stmt.bindString(31, sacspec);
        }
        stmt.bindLong(32, entity.getSacstatus());
 
        String savepath = entity.getSavepath();
        if (savepath != null) {
            stmt.bindString(33, savepath);
        }
 
        String stime = entity.getStime();
        if (stime != null) {
            stmt.bindString(34, stime);
        }
        stmt.bindString(35, entity.getStoragelocation());
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(36, subject);
        }
 
        String subjecttype = entity.getSubjecttype();
        if (subjecttype != null) {
            stmt.bindString(37, subjecttype);
        }
 
        String techcompany = entity.getTechcompany();
        if (techcompany != null) {
            stmt.bindString(38, techcompany);
        }
 
        String techdate = entity.getTechdate();
        if (techdate != null) {
            stmt.bindString(39, techdate);
        }
 
        String techopinion = entity.getTechopinion();
        if (techopinion != null) {
            stmt.bindString(40, techopinion);
        }
 
        String techperson = entity.getTechperson();
        if (techperson != null) {
            stmt.bindString(41, techperson);
        }
        stmt.bindLong(42, entity.getTechstaus());
        stmt.bindLong(43, entity.getTotalprice());
 
        String transferdate = entity.getTransferdate();
        if (transferdate != null) {
            stmt.bindString(44, transferdate);
        }
        stmt.bindLong(45, entity.getTypeid());
 
        String typename = entity.getTypename();
        if (typename != null) {
            stmt.bindString(46, typename);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(47, unit);
        }
        stmt.bindLong(48, entity.getUnitprice());
        stmt.bindLong(49, entity.getUsedeptid());
 
        String usedirection = entity.getUsedirection();
        if (usedirection != null) {
            stmt.bindString(50, usedirection);
        }
 
        String usepeople = entity.getUsepeople();
        if (usepeople != null) {
            stmt.bindString(51, usepeople);
        }
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(52, userid);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(53, username);
        }
 
        String voucherno = entity.getVoucherno();
        if (voucherno != null) {
            stmt.bindString(54, voucherno);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GZbean entity) {
        stmt.clearBindings();
 
        String accounttype = entity.getAccounttype();
        if (accounttype != null) {
            stmt.bindString(1, accounttype);
        }
 
        String approvalnumber = entity.getApprovalnumber();
        if (approvalnumber != null) {
            stmt.bindString(2, approvalnumber);
        }
        stmt.bindString(3, entity.getCreatetime());
 
        String deptname = entity.getDeptname();
        if (deptname != null) {
            stmt.bindString(4, deptname);
        }
 
        String entrydate = entity.getEntrydate();
        if (entrydate != null) {
            stmt.bindString(5, entrydate);
        }
 
        String etime = entity.getEtime();
        if (etime != null) {
            stmt.bindString(6, etime);
        }
 
        String factorynumber = entity.getFactorynumber();
        if (factorynumber != null) {
            stmt.bindString(7, factorynumber);
        }
 
        String fadate = entity.getFadate();
        if (fadate != null) {
            stmt.bindString(8, fadate);
        }
 
        String faopinion = entity.getFaopinion();
        if (faopinion != null) {
            stmt.bindString(9, faopinion);
        }
 
        String faperson = entity.getFaperson();
        if (faperson != null) {
            stmt.bindString(10, faperson);
        }
        stmt.bindLong(11, entity.getFastaus());
 
        String financialclass = entity.getFinancialclass();
        if (financialclass != null) {
            stmt.bindString(12, financialclass);
        }
 
        String fundssource = entity.getFundssource();
        if (fundssource != null) {
            stmt.bindString(13, fundssource);
        }
        stmt.bindLong(14, entity.getId());
 
        String lat = entity.getLat();
        if (lat != null) {
            stmt.bindString(15, lat);
        }
 
        String lng = entity.getLng();
        if (lng != null) {
            stmt.bindString(16, lng);
        }
 
        String macname = entity.getMacname();
        if (macname != null) {
            stmt.bindString(17, macname);
        }
        stmt.bindString(18, entity.getMid());
        stmt.bindString(19, entity.getNowstatus());
 
        String organization = entity.getOrganization();
        if (organization != null) {
            stmt.bindString(20, organization);
        }
 
        String projectnumber = entity.getProjectnumber();
        if (projectnumber != null) {
            stmt.bindString(21, projectnumber);
        }
 
        String purchasedate = entity.getPurchasedate();
        if (purchasedate != null) {
            stmt.bindString(22, purchasedate);
        }
 
        String qrphoto = entity.getQrphoto();
        if (qrphoto != null) {
            stmt.bindString(23, qrphoto);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(24, remark);
        }
 
        String sacmodel = entity.getSacmodel();
        if (sacmodel != null) {
            stmt.bindString(25, sacmodel);
        }
        stmt.bindString(26, entity.getSacname());
        stmt.bindString(27, entity.getSacno());
        stmt.bindLong(28, entity.getSacnumber());
 
        String sacphoto = entity.getSacphoto();
        if (sacphoto != null) {
            stmt.bindString(29, sacphoto);
        }
 
        String sacsource = entity.getSacsource();
        if (sacsource != null) {
            stmt.bindString(30, sacsource);
        }
 
        String sacspec = entity.getSacspec();
        if (sacspec != null) {
            stmt.bindString(31, sacspec);
        }
        stmt.bindLong(32, entity.getSacstatus());
 
        String savepath = entity.getSavepath();
        if (savepath != null) {
            stmt.bindString(33, savepath);
        }
 
        String stime = entity.getStime();
        if (stime != null) {
            stmt.bindString(34, stime);
        }
        stmt.bindString(35, entity.getStoragelocation());
 
        String subject = entity.getSubject();
        if (subject != null) {
            stmt.bindString(36, subject);
        }
 
        String subjecttype = entity.getSubjecttype();
        if (subjecttype != null) {
            stmt.bindString(37, subjecttype);
        }
 
        String techcompany = entity.getTechcompany();
        if (techcompany != null) {
            stmt.bindString(38, techcompany);
        }
 
        String techdate = entity.getTechdate();
        if (techdate != null) {
            stmt.bindString(39, techdate);
        }
 
        String techopinion = entity.getTechopinion();
        if (techopinion != null) {
            stmt.bindString(40, techopinion);
        }
 
        String techperson = entity.getTechperson();
        if (techperson != null) {
            stmt.bindString(41, techperson);
        }
        stmt.bindLong(42, entity.getTechstaus());
        stmt.bindLong(43, entity.getTotalprice());
 
        String transferdate = entity.getTransferdate();
        if (transferdate != null) {
            stmt.bindString(44, transferdate);
        }
        stmt.bindLong(45, entity.getTypeid());
 
        String typename = entity.getTypename();
        if (typename != null) {
            stmt.bindString(46, typename);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(47, unit);
        }
        stmt.bindLong(48, entity.getUnitprice());
        stmt.bindLong(49, entity.getUsedeptid());
 
        String usedirection = entity.getUsedirection();
        if (usedirection != null) {
            stmt.bindString(50, usedirection);
        }
 
        String usepeople = entity.getUsepeople();
        if (usepeople != null) {
            stmt.bindString(51, usepeople);
        }
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(52, userid);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(53, username);
        }
 
        String voucherno = entity.getVoucherno();
        if (voucherno != null) {
            stmt.bindString(54, voucherno);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 13);
    }    

    @Override
    public GZbean readEntity(Cursor cursor, int offset) {
        GZbean entity = new GZbean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // accounttype
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // approvalnumber
            cursor.getString(offset + 2), // createtime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // deptname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // entrydate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // etime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // factorynumber
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // fadate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // faopinion
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // faperson
            cursor.getInt(offset + 10), // fastaus
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // financialclass
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // fundssource
            cursor.getLong(offset + 13), // id
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // lat
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // lng
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // macname
            cursor.getString(offset + 17), // mid
            cursor.getString(offset + 18), // nowstatus
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // organization
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // projectnumber
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // purchasedate
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // qrphoto
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // remark
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // sacmodel
            cursor.getString(offset + 25), // sacname
            cursor.getString(offset + 26), // sacno
            cursor.getInt(offset + 27), // sacnumber
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // sacphoto
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // sacsource
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // sacspec
            cursor.getInt(offset + 31), // sacstatus
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // savepath
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // stime
            cursor.getString(offset + 34), // storagelocation
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // subject
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // subjecttype
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // techcompany
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // techdate
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // techopinion
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // techperson
            cursor.getInt(offset + 41), // techstaus
            cursor.getInt(offset + 42), // totalprice
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43), // transferdate
            cursor.getInt(offset + 44), // typeid
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // typename
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // unit
            cursor.getInt(offset + 47), // unitprice
            cursor.getInt(offset + 48), // usedeptid
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49), // usedirection
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // usepeople
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51), // userid
            cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52), // username
            cursor.isNull(offset + 53) ? null : cursor.getString(offset + 53) // voucherno
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GZbean entity, int offset) {
        entity.setAccounttype(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setApprovalnumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreatetime(cursor.getString(offset + 2));
        entity.setDeptname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEntrydate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEtime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFactorynumber(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFadate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFaopinion(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFaperson(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFastaus(cursor.getInt(offset + 10));
        entity.setFinancialclass(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFundssource(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setId(cursor.getLong(offset + 13));
        entity.setLat(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setLng(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setMacname(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMid(cursor.getString(offset + 17));
        entity.setNowstatus(cursor.getString(offset + 18));
        entity.setOrganization(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setProjectnumber(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setPurchasedate(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setQrphoto(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setRemark(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setSacmodel(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setSacname(cursor.getString(offset + 25));
        entity.setSacno(cursor.getString(offset + 26));
        entity.setSacnumber(cursor.getInt(offset + 27));
        entity.setSacphoto(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setSacsource(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setSacspec(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setSacstatus(cursor.getInt(offset + 31));
        entity.setSavepath(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setStime(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setStoragelocation(cursor.getString(offset + 34));
        entity.setSubject(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setSubjecttype(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setTechcompany(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setTechdate(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setTechopinion(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setTechperson(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setTechstaus(cursor.getInt(offset + 41));
        entity.setTotalprice(cursor.getInt(offset + 42));
        entity.setTransferdate(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
        entity.setTypeid(cursor.getInt(offset + 44));
        entity.setTypename(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setUnit(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setUnitprice(cursor.getInt(offset + 47));
        entity.setUsedeptid(cursor.getInt(offset + 48));
        entity.setUsedirection(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
        entity.setUsepeople(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setUserid(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
        entity.setUsername(cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52));
        entity.setVoucherno(cursor.isNull(offset + 53) ? null : cursor.getString(offset + 53));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GZbean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GZbean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
