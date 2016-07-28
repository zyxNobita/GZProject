package com.hr.nipuream.gz.controller.other.task;

import android.content.res.AssetManager;

import com.hr.nipuream.gz.GZApplication;

import java.io.InputStream;
import java.util.Properties;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-21 20:40
 * 邮箱：571829491@qq.com
 */
public class OtherFactoryConfig {

    private static Properties pro = new Properties();

    static{
        try {
            InputStream inputStream = GZApplication.getInstance().getApplicationContext().getAssets().open("other.properties", AssetManager.ACCESS_BUFFER);
            pro.load(inputStream);
        } catch (Exception e) {
        }
    }

    public  String getTaskClassName(String className){
        if(pro != null){
            return pro.getProperty(className);
        }
        return null;
    }


    private static OtherFactoryConfig instance;

    public synchronized static OtherFactoryConfig getInstance(){
        if(instance == null){
            synchronized (OtherFactoryConfig.class){
                instance = new OtherFactoryConfig();
            }
        }
        return instance;
    }
}
