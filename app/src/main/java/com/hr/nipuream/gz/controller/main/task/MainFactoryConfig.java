package com.hr.nipuream.gz.controller.main.task;

import android.content.res.AssetManager;

import com.hr.nipuream.gz.GZApplication;

import java.io.InputStream;
import java.util.Properties;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-23 17:28
 * 邮箱：571829491@qq.com
 */
public class MainFactoryConfig {

    private static Properties pro = new Properties();

    static{
        try {
            InputStream inputStream = GZApplication.getInstance().getApplicationContext().getAssets().open("main.properties", AssetManager.ACCESS_BUFFER);
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

    private static MainFactoryConfig instance;

    public synchronized static MainFactoryConfig getInstance(){
        if(instance == null){
            synchronized (MainFactoryConfig.class){
                instance = new MainFactoryConfig();
            }
        }
        return instance;
    }

}
