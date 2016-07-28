package com.hr.nipuream.gz.controller.other.task.net;

import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetTaskImpl;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-24 12:13
 * 邮箱：571829491@qq.com
 */
public abstract class UpdateBaseTask extends NetTaskImpl{

    public static final String UPDATE_CHECK_URL = Constants.BASE_URL + Constants.UPDATE_CHECK;
    public static final String UPDATE_CHECK_TAG = "update.check.tag";

    public static final String TYPE_ID = "typeid";




}
