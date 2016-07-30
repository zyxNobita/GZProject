package com.hr.nipuream.gz.controller.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZRepairBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.util.Util;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Date;

import carbon.widget.Button;
import carbon.widget.EditText;
import carbon.widget.TextView;
import carbon.widget.Toolbar;

public class AddGZRepair extends BaseActivity {

    private EditText scanoEt,repairEt,proDescEt;
    private TextView chooseTimeTv;
    private Button commitBtn;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try{
            switch (msg.what){
                case BaseActivity.USER_CHOOSE_TIME:
                {

                    CalendarDay date = (CalendarDay) msg.obj;
                    Date value = date.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String result = sdf.format(value);
                    if(!TextUtils.isEmpty(result))
                        chooseTimeTv.setText(result);

                }
                break;
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gzrepair);
        setViews();
    }

    private  GZRepairBaseTask gzRepairBaseTask;

    @Override
    public void registerTask() {
        super.registerTask();
        try{
            gzRepairBaseTask = (GZRepairBaseTask) MainBeanFactory.getInstance().newMainInstance("gzpair");
        }catch (Exception e){
            Logger.getLogger().w(e.toString());
        }
    }

    private void addGZRepair(String scano,String repairTime,String repairPersion,String problemDesc,String willComTime){
        Bundle bundle = new Bundle();
        String userId = SpUtil.getInstance().getValueFromXml(SpUtil.SP_GZ_USER_ID);
        if(!TextUtils.isEmpty(userId))
            bundle.putString(GZRepairBaseTask.USER_ID,userId);
        bundle.putString(GZRepairBaseTask.SCAN_NO,scano);
        bundle.putString(GZRepairBaseTask.REPAIR_TIME,repairTime);
        bundle.putString(GZRepairBaseTask.REPAIR_PERSON,repairPersion);
        bundle.putString(GZRepairBaseTask.PROBLEM_DESC,problemDesc);
        bundle.putString(GZRepairBaseTask.WILL_COMPELE_TIME,willComTime);

        gzRepairBaseTask.addOneBean(bundle, NetQueryStyle.VOLLEY);

    }

    private void setViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gz_add_repair));

        scanoEt = (EditText)findViewById(R.id.add_gz_repair_scano_et);
        repairEt = (EditText)findViewById(R.id.add_gz_repair_persion_et);
        proDescEt = (EditText)findViewById(R.id.gz_add_repair_item_pro_desc_et);
        chooseTimeTv = (TextView)findViewById(R.id.gz_add_repair_item_will_compele_time_select);
        commitBtn = (Button)findViewById(R.id.submit_btn);

        chooseTimeTv.setOnClickListener(this);
        commitBtn.setOnClickListener(this);

    }

    @Override
    public void clickView(View view) {
        super.clickView(view);
        switch (view.getId()){
            case R.id.gz_add_repair_item_will_compele_time_select:
            {
                PopTimerChoose();
            }
            break;
            case R.id.submit_btn:
            {

                String scano = scanoEt.getText().toString();
                if(TextUtils.isEmpty(scano))
                {
                    showToast(getString(R.string.gz_scano_not_empty));
                    return;
                }

                String repairPersion = repairEt.getText().toString();
                if(TextUtils.isEmpty(repairPersion))
                {
                    showToast(getString(R.string.gz_fix_persion_not_empty));
                    return;
                }

                String ProblemDesc = proDescEt.getText().toString();
                if(TextUtils.isEmpty(ProblemDesc))
                {
                    showToast(getString(R.string.gz_problem_desc_not_empty));
                    return;
                }

                String willCompleteTime = chooseTimeTv.getText().toString();
                addGZRepair(scano, Util.getNowTime(),repairPersion,ProblemDesc,willCompleteTime);

            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        this.finish();
    }

    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);
        try{

            if(object instanceof GZRepairBaseTask){

                int method = ((GZRepairBaseTask) object).getExecuteMethod();
                if(method == NetQueryMethod.ADD_ONE){
                    int resultCode = ((GZRepairBaseTask) object).getResultCode();
                    switch (resultCode){
                        case NetTaskInterface.REQUEST_SUCCESSFUL:
                        {
                            showToast(getString(R.string.gz_add_successfully));
                            onBackPressed();
                        }
                        break;
                        default:
                            String errorMessage = ((GZRepairBaseTask) object).getResultMessage();
                            if(!TextUtils.isEmpty(errorMessage))
                                showToast(errorMessage);
                            break;
                    }
                }
            }

        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        try{
            chooseTimeTv.setOnClickListener(null);
            commitBtn.setOnClickListener(null);
        }catch (Exception e){}
        super.onDestroy();
    }
}
