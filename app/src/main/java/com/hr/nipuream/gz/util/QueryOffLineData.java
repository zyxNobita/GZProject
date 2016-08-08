package com.hr.nipuream.gz.util;

import android.os.Bundle;
import android.text.TextUtils;

import com.hr.nipuream.gz.controller.main.task.net.GZBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetUtil;

import java.util.concurrent.Callable;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-08-06 17:42
 * 邮箱：571829491@qq.com
 */
public class QueryOffLineData implements Callable<String>{

    @Override
    public String call() throws Exception {
        String lastId = SpUtil.getInstance().getValueFromXml(SpUtil.SP_GZ_LIST_LAST_UPDATE_TIME);
        String requestTime = TextUtils.isEmpty(lastId)?"2015-01-01 00:00:00":lastId;

        Bundle bundle = new Bundle();
        bundle.putString("ak", Constants.KEY);
        bundle.putString(GZBaseTask.ID,requestTime);
        bundle.putString(GZBaseTask.TYPE_ID,"1");
        bundle.putString(GZBaseTask.EVERY_PAGE,"1000000");
        bundle.putString(GZBaseTask.CURRENT_PAGE,"1");


        String data =  NetUtil.OpenUrl(Constants.BASE_URL+Constants.QUERY_GZ_LIST,"POST",bundle,null);
        return data;
    }






}
