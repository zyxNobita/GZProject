package com.hr.nipuream.gz.controller.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.other.bean.LoginBean;
import com.hr.nipuream.gz.controller.other.task.OtherBeanFactory;
import com.hr.nipuream.gz.controller.other.task.net.LoginBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;

import carbon.widget.Button;
import carbon.widget.Toolbar;

public class Login extends BaseActivity {

    private EditText userNameEt,pwdEt;
    private Button submitBtn;
    private LoginBaseTask loginBaseTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initTitleBar();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.login));

        setViews();
    }

    @Override
    public void registerTask() {
        super.registerTask();
        loginBaseTask = (LoginBaseTask) OtherBeanFactory.getInstance().newOthersInstance("login");
    }

    private void login(String userName, String pwd, String typeId){
        try{
            Bundle bundle = new Bundle();
            bundle.putString(LoginBaseTask.USER_NAME,userName);
            bundle.putString(LoginBaseTask.PASS_WORD,pwd);
            bundle.putString(LoginBaseTask.TYPE_ID,typeId);
            loginBaseTask.addOneBean(bundle, NetQueryStyle.VOLLEY);
        }catch (Exception e){
            Logger.getLogger().e("login error");
        }
    }

    private void setViews(){
//        LinearLayout goBack = (LinearLayout)findViewById(R.id.header_back);
//        goBack.setVisibility(View.INVISIBLE);
//        TextView title = (TextView)findViewById(R.id.header_title);
//        title.setText(this.getResources().getString(R.string.login));
        userNameEt = (EditText)findViewById(R.id.login_user_name_et);
        pwdEt = (EditText)findViewById(R.id.login_user_pwd);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);
    }

    private String userName,pwd;

    @Override
    public void clickView(View view) {
        super.clickView(view);
        int id = view.getId();
        if(id == R.id.submit_btn){
            userName = userNameEt.getText().toString();
            pwd = pwdEt.getText().toString();

            if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd))
            {
                showToast(getString(R.string.login_error));
                return;
            }

            PopLoadDialog(this);
            login(userName,pwd,"1");
        }
    }


    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);

        dismissLoadDialog();
        if(object instanceof LoginBaseTask){
            int method = ((LoginBaseTask) object).getExecuteMethod();
            if(method == NetQueryMethod.ADD_ONE){
                int resultCode = ((LoginBaseTask) object).getResultCode();
                if(resultCode == NetTaskInterface.REQUEST_SUCCESSFUL){
                    LoginBean  loginBean =
                            (LoginBean) ((LoginBaseTask) object).getBean();
                    showToast(loginBean.getMessage());
                    SpUtil.getInstance().insertToxml(userName,pwd);
                    SpUtil.getInstance().insertToxml(SpUtil.SP_ISLOGIN,"true");

                    startActivity(new Intent(this,MainActivity.class));
                    this.finish();

                }else
                    showToast(getResources().getString(R.string.net_error));
            }
        }
    }



}
