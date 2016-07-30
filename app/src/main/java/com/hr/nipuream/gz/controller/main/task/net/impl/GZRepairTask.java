package com.hr.nipuream.gz.controller.main.task.net.impl;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.hr.nipuream.gz.controller.main.bean.GZRepairbean;
import com.hr.nipuream.gz.controller.main.task.net.GZRepairBaseTask;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetQueryMethod;
import org.json.JSONObject;
import java.util.List;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-29 15:16
 * 邮箱：571829491@qq.com
 */
public class GZRepairTask extends GZRepairBaseTask{

    @Override
    protected void execSuccess(JSONObject jsonObject, int method) {
        try{

            switch (method){
                case NetQueryMethod.QUERY_ALL:
                {
                    resultMessage = jsonObject.optString("message");
                    pages = jsonObject.optJSONObject("data").optInt("totalPages");
                    if(Integer.parseInt(jsonObject.optString("status"))==0){
                        ts = getGson().fromJson(jsonObject.optJSONObject("data").optJSONArray("result").toString(),
                                new TypeToken<List<GZRepairbean>>(){}.getType());
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
                case NetQueryMethod.ADD_ONE:
                {
                    resultMessage = jsonObject.optString("message");
                    if(Integer.parseInt(jsonObject.optString("status"))==0){
                        setCallBackResponse(REQUEST_SUCCESSFUL);
                    }else
                        setCallBackResponse(REQUEST_ERROR);
                }
                    break;
            }
        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
    }

    @Override
    public List queryBeans(Bundle bundle, int queryStyle) {
        try{

            getParams().clear();
            getParams().put("ak", Constants.KEY);
            getParams().put("typeid","1");
            getParams().put("id","0");
            if(!TextUtils.isEmpty(bundle.getString(USER_ID))){getParams().put(USER_ID,bundle.getString(USER_ID));}
            if(!TextUtils.isEmpty(bundle.getString(SCAN_NO))){getParams().put(SCAN_NO,bundle.getString(SCAN_NO));}
            if(!TextUtils.isEmpty(bundle.getString(EVERYPAGE))){getParams().put(EVERYPAGE,bundle.getString(EVERYPAGE));}
            if(!TextUtils.isEmpty(bundle.getString(CURRENTPAGE))){getParams().put(CURRENTPAGE,bundle.getString(CURRENTPAGE));}

            executeNetTask(NetQueryMethod.QUERY_ALL,queryStyle,QUERY_REPAIR_LIST_URL,QUERY_REPAIR_LIST_TAG,getParams());

        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
        return super.queryBeans(bundle, queryStyle);
    }


    @Override
    public void addOneBean(Bundle bundle, int queryStyle) {
        try{

            getParams().clear();
            getParams().put("ak",Constants.KEY);
            getParams().put("typeid","1");
            if(!TextUtils.isEmpty(bundle.getString(USER_ID))){getParams().put(USER_ID,bundle.getString(USER_ID));}
            if(!TextUtils.isEmpty(bundle.getString(SCAN_NO))){getParams().put(SCAN_NO,bundle.getString(SCAN_NO));}
            if(!TextUtils.isEmpty(bundle.getString(REPAIR_TIME))){getParams().put(REPAIR_TIME,bundle.getString(REPAIR_TIME));}
            if(!TextUtils.isEmpty(bundle.getString(REPAIR_PERSON))){getParams().put(REPAIR_PERSON,bundle.getString(REPAIR_PERSON));}
            if(!TextUtils.isEmpty(bundle.getString(PROBLEM_DESC))){getParams().put(PROBLEM_DESC,bundle.getString(PROBLEM_DESC));}
            if(!TextUtils.isEmpty(bundle.getString(WILL_COMPELE_TIME))){getParams().put(WILL_COMPELE_TIME,bundle.getString(WILL_COMPELE_TIME));}

            executeNetTask(NetQueryMethod.ADD_ONE,queryStyle,ADD_REPAIR_RECORD,ADD_REPAIR_RECODE_TAG,getParams());



        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
        super.addOneBean(bundle, queryStyle);
    }
}
