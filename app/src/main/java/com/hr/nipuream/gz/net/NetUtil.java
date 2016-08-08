package com.hr.nipuream.gz.net;

import android.os.Bundle;

import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.util.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述：网络工具类
 * 作者：Nipuream
 * 时间: 2016-08-06 16:55
 * 邮箱：571829491@qq.com
 */
public class NetUtil {


    public static final int NET_UTIL_PROGRESS_UPDATE = 0x887;
    public static final int NET_UTIL_EXECUTE_COMPELETE = 0x888;
    public static final int NET_UTIL_EXECUTE_ERROR = 0x889;

    public static String OpenUrl(String url, String method, Bundle params,
                                 String enc) {
        String response = null;
        if (method.equals("GET")) {
            if (params != null)
                url = url + "?" + encodeUrl(params);
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("User-Agent", System.getProperties()
                    .getProperty("http.agent"));
            if (method.equals("POST")) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                if (params != null)
                    conn.getOutputStream().write(
                            encodeUrl(params).getBytes("UTF-8"));
            }
            int maxLength = conn.getContentLength();
            int readLength = maxLength/100;
            response = read(conn.getInputStream(), enc,readLength);
        } catch (Exception e) {
            response = "";
            Logger.getLogger().e(e.toString());
            BaseActivity.getMyHandler().obtainMessage(NET_UTIL_EXECUTE_ERROR).sendToTarget();
            // throw new RuntimeException(e.getMessage(), e);
        }
        return response;
    }

    private static String read(InputStream in, String enc,int readLength) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buff = new byte[readLength];
        int ts = -1;

        int progress = 0;

        while((ts = in.read(buff))!=-1){
            BaseActivity.getMyHandler().obtainMessage(
                    NET_UTIL_PROGRESS_UPDATE,++progress).sendToTarget();
            baos.write(buff,0,ts);
        }

        BaseActivity.getMyHandler().obtainMessage(NET_UTIL_EXECUTE_COMPELETE).sendToTarget();

        String result = (enc == null)?new String(baos.toByteArray()):new String(baos.toByteArray(),enc);
        baos.close();
        in.close();

        return result;
    }

    public static String encodeUrl(Bundle parameters) {
        if (parameters == null)
            return "";
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            if (first)
                first = false;
            else
                sb.append("&");
            sb.append(key + "=" + parameters.getString(key));
        }
        return sb.toString();
    }


}
