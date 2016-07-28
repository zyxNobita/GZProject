package com.hr.nipuream.gz.net;

import android.os.Bundle;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.gson.Gson;
import com.hr.nipuream.gz.GZApplication;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：抽象网络接口类
 * 作者：Nipuream
 * 时间: 2016-07-21 20:04
 * 邮箱：571829491@qq.com
 */
public abstract class NetTaskImpl<T> implements NetTaskInterface {

    /**
     * 请求结果
     */
    protected int resultCode = REQUEST_ERROR;

    /**
     * 请求结果的页数
     */
    protected int pages = 1;

    private Gson gson = new Gson();


    private Bundle bundle = new Bundle();

    protected String resultMessage;

    public String getResultMessage(){
        return resultMessage;
    }

    public Bundle getBundle(){
        return bundle;
    }

    protected int executeMethod = NetQueryMethod.QUERY;

    private Map<String,String> params = new HashMap<>();

    public Map<String,String> getParams(){
        return params;
    }

    protected  T t = null;

    public T getBean(){
        return t;
    }

    protected List<T> ts = new ArrayList<>();

     public List<T> getBeans(){
         return ts;
     }

    public void setCallBackResponse(int resultCode){
        this.resultCode = resultCode;
        EventBus.getDefault().post(this);
    }

    public int getExecuteMethod(){
        return executeMethod;
    }

    public int getResultCode(){
        return resultCode;
    }

    public int getPages(){
        return pages;
    }

    public Gson getGson(){
        return gson;
    }

    /**
     * 假如服务器给的所有的条目，则计算页数
     * @param total
     * @return
     */
    public int calPages(int total){

        if(total == 0)
        {
            return 1;
        }

        int pages = 1;
        if(total % 10 == 0){
            pages = total /10;
        }else{
            pages = total/10 + 1;
        }
        return pages;
    }

    public void executeNetTask(final int method,int queryStyle,String url,
                               String tag,final Map<String,String> params){

        switch (queryStyle){
            case NetQueryStyle.VOLLEY:
            {
                this.executeMethod = method;
                GZApplication.getInstance().getmQueue().cancelAll(tag);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        JSONObject jsonObject = null;
                        try{
                            jsonObject = new JSONObject(s);
                        }catch (Exception e){
//                    callBackResult(NoteTaskOver.RESULT_ERROR,null,0);
                            resultCode = REQUEST_ERROR;
                            EventBus.getDefault().post(this);
                        }

                        if(jsonObject == null){
                            return;
                        }

                        execSuccess(jsonObject,method);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        resultCode = REQUEST_ERROR;
                        EventBus.getDefault().post(this);
//                callBackResult(NoteTaskOver.RESULT_ERROR,null,0);
                    }
                }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return params;
                    }
                };

                stringRequest.setTag(tag);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                GZApplication.getInstance().getmQueue().add(stringRequest);
            }
                break;
            default:break;
        }
    }


    protected abstract void execSuccess(JSONObject jsonObject,int method);

    @Override
    public List queryBeans(Bundle bundle, int queryStyle) {
        return null;
    }

    @Override
    public Object queryOneBean(Bundle bundle, int queryStyle) {
        return null;
    }

    @Override
    public void addOneBean(Bundle bundle, int queryStyle) {

    }

    @Override
    public void updateHrBean(Bundle bundle, int queryStyle) {

    }

    @Override
    public void deleteHrBean(Bundle bundle, int queryStyle) {

    }

    @Override
    public void doOtherOne(Bundle bundle, int queryStyle) {

    }

    @Override
    public void doOtherTwo(Bundle bundle, int queryStyle) {

    }

    @Override
    public void doOtherThree(Bundle bundle, int queryStyle) {

    }


}
