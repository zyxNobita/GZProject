package com.hr.nipuream.gz.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.hr.nipuream.gz.GZApplication;

/**
 * 描述：xml保存
 * 作者：Nipuream
 * 时间: 2016-07-23 16:57
 * 邮箱：571829491@qq.com
 */
public class SpUtil {

    private static SpUtil instance;
    private SharedPreferences pre;
    private SharedPreferences.Editor editor;

    public static final String SP_ISLOGIN = "sp.islogin";

    /**
     * 根据ID去判断是否更新
     */
    public static final String SP_GZ_LIST_LAST_UPDATE_TIME = "sp.gzlist.last.update.time";

    /**
     * UserId
     */
    public static final String SP_GZ_USER_ID = "sp.gz.userid";

    private SpUtil(){
        pre = GZApplication.getInstance().getSharedPreferences(
                "com.hr.nipuream.gz", Context.MODE_PRIVATE);
        editor = pre.edit();
    }

    public synchronized  static SpUtil getInstance(){
        if(instance == null){
            synchronized (SpUtil.class){
                instance = new SpUtil();
            }
        }
        return instance;
    }

    public void insertToxml(String name,String value){
        editor.putString(name,value).commit();
    }

    public String getValueFromXml(String name){
        return pre.getString(name,"");
    }

}
