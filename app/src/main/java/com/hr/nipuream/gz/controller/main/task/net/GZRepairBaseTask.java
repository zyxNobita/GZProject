package com.hr.nipuream.gz.controller.main.task.net;

import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetTaskImpl;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-28 20:27
 * 邮箱：571829491@qq.com
 */
public abstract class GZRepairBaseTask extends NetTaskImpl {


    public static final String QUERY_REPAIR_LIST_URL = Constants.BASE_URL + Constants.QUERY_REPAIR_LIST;
    public static final String QUERY_REPAIR_LIST_TAG = "query.repair.list.tag";

    public static final String USER_ID = "userid";
    public static final String SCAN_NO = "sacno";

    public static final String EVERYPAGE = "everypage";
    public static final String CURRENTPAGE = "currentpage";


    public static final String ADD_REPAIR_RECORD = Constants.BASE_URL + Constants.ADD_REPAIR_RECODE;
    public static final String ADD_REPAIR_RECODE_TAG = "add.repair.record.tag";

    public static final String REPAIR_TIME = "maintaintime";
    public static final String REPAIR_PERSON = "maintainman";
    public static final String PROBLEM_DESC = "problemdesc";
    public static final String WILL_COMPELE_TIME = "ectime";

}
