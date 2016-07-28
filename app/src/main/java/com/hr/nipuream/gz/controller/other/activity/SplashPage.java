package com.hr.nipuream.gz.controller.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.dao.SpUtil;

public class SplashPage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        getMyHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String isLogin = SpUtil.getInstance().getValueFromXml(SpUtil.SP_ISLOGIN);
                if(TextUtils.equals(isLogin,"true"))
                    startActivity(new Intent(SplashPage.this,MainActivity.class));
                else
                    startActivity(new Intent(SplashPage.this,Login.class));
                finish();

            }
        },3000);

    }

}
