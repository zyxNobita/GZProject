package com.hr.nipuream.gz.controller.other.task.net.impl;

import android.os.Bundle;
import android.text.TextUtils;
import com.hr.nipuream.gz.controller.other.bean.LoginBean;
import com.hr.nipuream.gz.controller.other.task.net.LoginBaseTask;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetQueryMethod;
import org.json.JSONObject;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-21 20:51
 * 邮箱：571829491@qq.com
 */
public class LoginTask extends LoginBaseTask{

    @Override
    protected void execSuccess(JSONObject jsonObject, int method) {

        try{

            switch (method){

                case NetQueryMethod.ADD_ONE:
                {
                    if(Integer.parseInt(jsonObject.optString("status"))==0){

                        t = getGson().fromJson(jsonObject.toString(), LoginBean.class);
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
    public void addOneBean(Bundle bundle, int queryStyle) {
        super.addOneBean(bundle, queryStyle);
        try{

            getParams().clear();
            getParams().put("ak", Constants.KEY);
            if(!TextUtils.isEmpty(bundle.getString(USER_NAME))){getParams().put(USER_NAME,bundle.getString(USER_NAME));}
            if(!TextUtils.isEmpty(bundle.getString(PASS_WORD))){getParams().put(PASS_WORD,bundle.getString(PASS_WORD));}
            if(!TextUtils.isEmpty(bundle.getString(TYPE_ID))){getParams().put(TYPE_ID,bundle.getString(TYPE_ID));}

            executeNetTask(NetQueryMethod.ADD_ONE,queryStyle,LOGIN_URL,LOGIN_TAG,getParams());

        }catch (Exception e){
            setCallBackResponse(REQUEST_ERROR);
        }
    }
}
