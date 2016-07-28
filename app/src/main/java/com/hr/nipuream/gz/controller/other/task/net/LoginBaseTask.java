package com.hr.nipuream.gz.controller.other.task.net;

import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetTaskImpl;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-21 20:49
 * 邮箱：571829491@qq.com
 */
public abstract class LoginBaseTask extends NetTaskImpl{

    public static final String LOGIN_URL = Constants.BASE_URL + Constants.LOGIN;
    public static final String LOGIN_TAG = "login.tag";

    public static final String USER_NAME = "username";
    public static final String PASS_WORD = "password";
    public static final String TYPE_ID = "typeid";

}
