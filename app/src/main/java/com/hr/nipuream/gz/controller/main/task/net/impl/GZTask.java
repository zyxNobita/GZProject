package com.hr.nipuream.gz.controller.main.task.net.impl;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.controller.main.task.net.GZBaseTask;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetQueryMethod;
import org.json.JSONObject;
import java.util.List;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-23 19:29
 * 邮箱：571829491@qq.com
 */
public class GZTask extends GZBaseTask{

    @Override
    protected void execSuccess(JSONObject jsonObject, int method) {

        try{
            switch (method){
                case NetQueryMethod.QUERY:
                {
                    resultMessage = jsonObject.optString("message");

                    if(Integer.parseInt(jsonObject.optString("status"))==0) {
                        t = getGson().fromJson(jsonObject.optString("data").toString(), GZbean.class);
                        setCallBackResponse(REQUEST_SUCCESSFUL);
                    }
                    else
                        setCallBackResponse(REQUEST_ERROR);
                }
                break;
                case NetQueryMethod.QUERY_ALL:
                {
                    resultMessage = jsonObject.optString("message");
                    getBundle().putString("maxtime",jsonObject.optString("maxtime"));
                    pages = Integer.parseInt(jsonObject.optJSONObject("data").optString("totalPages"));

                    if(Integer.parseInt(jsonObject.optString("status"))==0){

                        ts = getGson().fromJson(jsonObject.optJSONObject("data").optJSONArray("result").toString(),new TypeToken<List<GZbean>>(){}.getType());
                        pages = jsonObject.optInt("totalPages");

                        if(ts.size() == 0){
                            setCallBackResponse(REQUEST_EMPTY);
                            return;
                        }

                        setCallBackResponse(REQUEST_SUCCESSFUL);
                    }
                    else
                        setCallBackResponse(REQUEST_ERROR);
                }
                    break;
            }
        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
    }

    @Override
    public Object queryOneBean(Bundle bundle, int queryStyle) {
        try{

            getParams().clear();
            getParams().put("ak", Constants.KEY);
            if(!TextUtils.isEmpty(bundle.getString(SCAN_NO))){getParams().put(SCAN_NO,bundle.getString(SCAN_NO));}
            if(!TextUtils.isEmpty(bundle.getString(TYPE_ID))){getParams().put(TYPE_ID,bundle.getString(TYPE_ID));}

            executeNetTask(NetQueryMethod.QUERY,queryStyle,GZBaseTask.QUERY_GZ_DETAIL_URL,GZBaseTask.QUERY_GZ_DETAIL_TAG,getParams());

        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
        return super.queryOneBean(bundle, queryStyle);
    }

    @Override
    public List queryBeans(Bundle bundle, int queryStyle) {
        try{

            getParams().clear();
            getParams().put("ak",Constants.KEY);
            if(!TextUtils.isEmpty(bundle.getString(ID))){getParams().put(ID,bundle.getString(ID));}
            if(!TextUtils.isEmpty(bundle.getString(TYPE_ID))){getParams().put(TYPE_ID,bundle.getString(TYPE_ID));}
            if(!TextUtils.isEmpty(bundle.getString(SCAN_NO))){getParams().put(SCAN_NO,bundle.getString(SCAN_NO));}
            if(!TextUtils.isEmpty(bundle.getString(EVERY_PAGE))){getParams().put(EVERY_PAGE,bundle.getString(EVERY_PAGE));}
            if(!TextUtils.isEmpty(bundle.getString(CURRENT_PAGE))){getParams().put(CURRENT_PAGE,bundle.getString(CURRENT_PAGE));}

            executeNetTask(NetQueryMethod.QUERY_ALL,queryStyle,GZBaseTask.QUERY_GZ_LIST_URL,GZBaseTask.QUERY_GZ_LIST_TAG,getParams());

        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
        return super.queryBeans(bundle, queryStyle);
    }
}
