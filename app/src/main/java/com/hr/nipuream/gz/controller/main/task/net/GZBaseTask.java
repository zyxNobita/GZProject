package com.hr.nipuream.gz.controller.main.task.net;

import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetTaskImpl;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-23 17:34
 * 邮箱：571829491@qq.com
 */
public abstract  class GZBaseTask extends NetTaskImpl{

    /**
     * 详情
     */
    public static final String QUERY_GZ_DETAIL_URL = Constants.BASE_URL + Constants.QUERY_GZ_DETAIL;
    public static final String QUERY_GZ_DETAIL_TAG = "query.gz.detail.tag";


    public static final String SCAN_NO = "sacno";
    public static final String TYPE_ID = "typeid";


    /**
     * 列表
     */
    public static final String QUERY_GZ_LIST_URL = Constants.BASE_URL + Constants.QUERY_GZ_LIST;
    public static final String QUERY_GZ_LIST_TAG = "query.gz.list.tag";

    public static final String ID = "id";
    public static final String EVERY_PAGE = "everypage";
    public static final String CURRENT_PAGE = "currentpage";


}
