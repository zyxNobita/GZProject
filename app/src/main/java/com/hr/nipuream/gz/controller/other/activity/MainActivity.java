package com.hr.nipuream.gz.controller.other.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.activity.GZDetail;
import com.hr.nipuream.gz.controller.main.fragment.FirstFragment;
import com.hr.nipuream.gz.controller.manager.fragment.GzManagerFragment;
import com.hr.nipuream.gz.controller.other.adapter.FirstPageAdapter;
import com.hr.nipuream.gz.controller.other.bean.UpdateBean;
import com.hr.nipuream.gz.controller.other.task.OtherBeanFactory;
import com.hr.nipuream.gz.controller.other.task.net.UpdateBaseTask;
import com.hr.nipuream.gz.controller.setting.fragment.SettingFragment;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.service.UpdateVersionServer;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private ViewPager vp;
    private TextView firstPageTv, gzManagerTv, settingTv;
    private List<Fragment> fragmentLists = new ArrayList<>();


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            switch (msg.what) {
                case DOWN_LOAD_APPLICATION:
                    boolean isDownLoad = (Boolean) msg.obj;
                    if (isDownLoad) {
                        //开启服务下载新版本软件
                        if (updateBean != null) {
                            Intent intent = new Intent(MainActivity.this, UpdateVersionServer.class);
                            intent.putExtra(UpdateVersionServer.NEW_VERSION_APP_INFORMATION, updateBean);
                            startService(intent);
                            showToast(getString(R.string.start_download));
                        }
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 当前Fragment
     */
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        createFragment();
        initVp();
        checkUpdate();
    }

    private UpdateBaseTask updateBaseTask;

    @Override
    protected void registerGZTask() {
        super.registerGZTask();
        updateBaseTask = (UpdateBaseTask) OtherBeanFactory.getInstance().newOthersInstance("update");
    }

    private void checkUpdate() {
        Bundle bundle = new Bundle();
        bundle.putString(UpdateBaseTask.TYPE_ID, "1");
        updateBaseTask.queryOneBean(bundle, NetQueryStyle.VOLLEY);
    }

    private void createFragment() {
        fragmentLists.add(FirstFragment.getInstance(null));
        fragmentLists.add(GzManagerFragment.getInstance(null));
        fragmentLists.add(SettingFragment.getInstance(null));
    }

    private void setViews() {
        vp = (ViewPager) findViewById(R.id.main_viewpager);
        firstPageTv = (TextView) findViewById(R.id.first_page_tv);
        settingTv = (TextView) findViewById(R.id.setting_tv);
        gzManagerTv = (TextView) findViewById(R.id.guozi_manager);

        firstPageTv.setOnClickListener(this);
        settingTv.setOnClickListener(this);
        gzManagerTv.setOnClickListener(this);
    }


    @Override
    public void clickView(View view) {
        super.clickView(view);
        switch (view.getId()) {
            case R.id.first_page_tv:
                vp.setCurrentItem(0);
                break;
            case R.id.setting_tv:
                vp.setCurrentItem(2);
                break;
            case R.id.guozi_manager:
                vp.setCurrentItem(1);
                break;
        }
    }

    private void initVp() {
        FirstPageAdapter adapter = new FirstPageAdapter(this.getSupportFragmentManager(), fragmentLists);
        vp.setAdapter(adapter);
    }

    private UpdateBean updateBean;

    public UpdateBean getUpdateBean(){
        return updateBean;
    }

    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);

        if (object instanceof UpdateBaseTask) {
            updateBean = (UpdateBean) ((UpdateBaseTask) object).getBean();
            Logger.getLogger().d(updateBean.toString());
            if (updateBean != null)
                if (!Util.getVerName().equals(updateBean.getVersionIndex())){
                        doNewVersionUpdate(getMyHandler(), updateBean);
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == FirstFragment.REQUEST_CODE_SCAN
                    && resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                String code = bundle.getString("result");

                Intent intent = new Intent(MainActivity.this, GZDetail.class);
                intent.putExtra("code", code);
                startActivity(intent);

            }
        } catch (Exception e) {
            Logger.getLogger().e(e.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
