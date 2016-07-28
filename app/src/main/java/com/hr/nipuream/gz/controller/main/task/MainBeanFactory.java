package com.hr.nipuream.gz.controller.main.task;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-23 17:28
 * 邮箱：571829491@qq.com
 */
public class MainBeanFactory {

    private static final String OTHER_TASK_NET = "com.hr.nipuream.gz.controller.main.task.net.impl.";

    private static MainBeanFactory instance;


    public static synchronized MainBeanFactory getInstance(){
        if(instance == null){
            synchronized (MainBeanFactory.class){
                instance = new MainBeanFactory();
            }
        }
        return instance;
    }

    public  Object newMainInstance(String className){
        Object o = null;
        try{
            Class c = null;
            c = Class.forName(OTHER_TASK_NET+ MainFactoryConfig.getInstance().getTaskClassName(className));
            o = c.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }

}
