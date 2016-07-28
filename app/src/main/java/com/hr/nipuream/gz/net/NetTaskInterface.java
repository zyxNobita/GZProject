package com.hr.nipuream.gz.net;

import android.os.Bundle;
import java.util.List;

/**
 * 描述：网络接口
 * 作者：Nipuream
 * 时间: 2016-07-21 19:27
 * 邮箱：571829491@qq.com
 */
public interface NetTaskInterface<T> {


    /**
     * 当网络执行成功，并返回数据
     */
    public static final int REQUEST_SUCCESSFUL = 0X688;

    /**
     * 当网路执行失败
     */
    public static final int REQUEST_ERROR = 0x689;

    /**
     * 当网络执行结果为空
     */
    public static final int REQUEST_EMPTY = 0x687;

    /**
     * 网络执行为其他结果
     */
    public static final int OTHER_ERROR = 0x686;



    /**
     * 查询列表
     * @param bundle 参数
     * @param queryStyle  请求方式
     * @return
     */
    public List<T> queryBeans(Bundle bundle,int queryStyle);


    /**
     * 查询单个JavaBean
     * @param bundle
     * @param queryStyle
     * @return
     */
    public T queryOneBean(Bundle bundle,int queryStyle);


    /**
     * 向服务器添加一个数据，这个没有返回值是因为EventBus异步返回给我们结果
     * @param bundle
     * @param queryStyle
     */
    public void addOneBean(Bundle bundle,int queryStyle);


    /**
     * 更新数据
     * @param bundle
     * @param queryStyle
     */
    public void updateHrBean(Bundle bundle,int queryStyle);


    /**
     * 删除数据
     * @param bundle
     * @param queryStyle
     */
    public void deleteHrBean(Bundle bundle,int queryStyle);


    /**
     * 空缺
     * @param bundle
     * @param queryStyle
     */
    public void doOtherOne(Bundle bundle,int queryStyle);

    /**
     * 空缺二
     * @param bundle
     * @param queryStyle
     */
    public void doOtherTwo(Bundle bundle,int queryStyle);


    /**
     * 空缺三
     * @param bundle
     * @param queryStyle
     */
    public void doOtherThree(Bundle bundle,int queryStyle);


}
