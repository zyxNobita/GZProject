package com.hr.nipuream.gz.controller.setting.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.base.BaseFragment;
import com.hr.nipuream.gz.controller.main.activity.GZRepair;
import com.hr.nipuream.gz.controller.other.activity.MainActivity;
import com.hr.nipuream.gz.controller.other.bean.UpdateBean;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.util.Util;

import carbon.widget.Button;
import carbon.widget.LinearLayout;
import carbon.widget.TextView;
import carbon.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    private TextView fixTv,aboutTv,versionUpdateTv;
    private LinearLayout versionLl;
    private Button loginOut;

    private UpdateBean updateBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.setting));

        updateBean = ((MainActivity)getActivity()).getUpdateBean();

        fixTv = (TextView)view.findViewById(R.id.setting_fix);
        aboutTv = (TextView)view.findViewById(R.id.about_gz);
        versionLl = (LinearLayout)view.findViewById(R.id.version_update);
        versionUpdateTv = (TextView)view.findViewById(R.id.version_tv);
        loginOut = (Button)view.findViewById(R.id.login_out);

        fixTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GZRepair.class));
            }
        });

        versionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateBean != null)
                    ((BaseActivity)getActivity()).doNewVersionUpdate(getMyHandler(), updateBean);
            }
        });

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.getInstance().insertToxml(SpUtil.SP_ISLOGIN,"false");
                GZApplication.getInstance().clearAct();
                System.exit(0);
            }
        });

        initState();
        return view;
    }

    private void initState(){
        try{
            if(Util.getVerName().equals(updateBean.getVersionIndex()))
            {
                versionUpdateTv.setVisibility(View.VISIBLE);
                versionUpdateTv.setBackgroundResource(R.drawable.no_update_version_tv_shape);
                versionUpdateTv.setText(Util.getVerName());
            }else{
                versionUpdateTv.setVisibility(View.VISIBLE);
                versionUpdateTv.setBackgroundResource(R.drawable.update_version_tv_shape);
                versionUpdateTv.setText(getString(R.string.has_new_version));
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

//    private UpdateBaseTask updateBaseTask;


//    @Override
//    public void registerTask() {
//        super.registerTask();
//        updateBaseTask = (UpdateBaseTask) OtherBeanFactory.getInstance().newOthersInstance("update");
//    }
//
//    private void checkUpdate() {
//        Bundle bundle = new Bundle();
//        bundle.putString(UpdateBaseTask.TYPE_ID, "1");
//        updateBaseTask.queryOneBean(bundle, NetQueryStyle.VOLLEY);
//    }

    public static SettingFragment getInstance(Bundle bundle){
        SettingFragment fragment = new SettingFragment();
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

//    private UpdateBean updateBean;
//
//    @Override
//    public void updateGzData(Object object) {
//        super.updateGzData(object);
//        try{
//
//            if(object instanceof UpdateBaseTask){
//
//                updateBean = (UpdateBean) ((UpdateBaseTask) object).getBean();
//                if(Util.getVerName().equals(updateBean.getVersionIndex()))
//                {
//                    versionUpdateTv.setVisibility(View.VISIBLE);
//                    versionUpdateTv.setBackgroundResource(R.drawable.no_update_version_tv_shape);
//                    versionUpdateTv.setText(Util.getVerName());
//                }else{
//                    versionUpdateTv.setVisibility(View.VISIBLE);
//                    versionUpdateTv.setBackgroundResource(R.drawable.update_version_tv_shape);
//                    versionUpdateTv.setText(getString(R.string.has_new_version));
//                }
//
//
//            }
//
//        }catch (Exception e){
//            Logger.getLogger().e(e.toString());
//        }
//    }



}
