package com.hr.nipuream.gz.controller.other.task.net.impl;

import android.os.Bundle;
import android.text.TextUtils;

import com.hr.nipuream.gz.controller.other.bean.UpdateBean;
import com.hr.nipuream.gz.controller.other.task.net.UpdateBaseTask;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetQueryMethod;

import org.json.JSONObject;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-24 12:15
 * 邮箱：571829491@qq.com
 */
public class UpdateTask extends UpdateBaseTask{


    @Override
    protected void execSuccess(JSONObject jsonObject, int method) {

        try{

            switch (method){
                case NetQueryMethod.QUERY:
                {
                    t = getGson().fromJson(jsonObject.toString(), UpdateBean.class);
                    setCallBackResponse(REQUEST_SUCCESSFUL);
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
            if(!TextUtils.isEmpty(bundle.getString(TYPE_ID))){getParams().put(TYPE_ID,bundle.getString(TYPE_ID));}

            executeNetTask(NetQueryMethod.QUERY,queryStyle,UPDATE_CHECK_URL,UPDATE_CHECK_TAG,getParams());

        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }

        return super.queryOneBean(bundle, queryStyle);
    }
}
