package com.hr.nipuream.gz.controller.other.task;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-21 20:44
 * 邮箱：571829491@qq.com
 */
public class OtherBeanFactory {


    private static final String OTHER_TASK_NET = "com.hr.nipuream.gz.controller.other.task.net.impl.";

    private static OtherBeanFactory instance;


    public static synchronized OtherBeanFactory getInstance(){
        if(instance == null){
            synchronized (OtherBeanFactory.class){
                instance = new OtherBeanFactory();
            }
        }
        return instance;
    }

    public  Object newOthersInstance(String className){
        Object o = null;
        try{
            Class c = null;
            c = Class.forName(OTHER_TASK_NET+ OtherFactoryConfig.getInstance().getTaskClassName(className));
            o = c.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }


}
