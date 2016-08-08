package com.hr.nipuream.gz.controller.main.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.base.BaseFragment;
import com.hr.nipuream.gz.controller.main.activity.GZList;
import com.hr.nipuream.gz.controller.main.activity.GZRepair;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.dao.db.GZbeanDao;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.zxing.MipcaActivityCapture;

import java.util.List;

import carbon.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment {


    public static final int REQUEST_CODE_SCAN = 0x643;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0x642;

    private GZbeanDao gZbeanDao;


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);

        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.gz_check);

        TextView tv = (TextView) view.findViewById(R.id.first_gz_scan_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.CAMERA},
//                            MY_PERMISSIONS_REQUEST_CAMERA);
                    //Fragment中使用这个方法
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }else{
                    //
                    getActivity().startActivityForResult(new Intent(getActivity(),
                            MipcaActivityCapture.class),REQUEST_CODE_SCAN);
                }
            }
        });

        TextView gzManager = (TextView) view.findViewById(R.id.first_gz_manager);
        gzManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GZList.class));
            }
        });

        TextView gzPair = (TextView) view.findViewById(R.id.first_gz_repair);
        gzPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GZRepair.class));
            }
        });

        TextView gzUpdate = (TextView)view.findViewById(R.id.update_gz_info);
        gzUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新数据
                ((BaseActivity)getActivity()).PopLoadDialog(getActivity());
                getData();
            }
        });

        return view;
    }

    private GZBaseTask gzBaseTask;

    @Override
    public void registerTask() {
        super.registerTask();
        gZbeanDao = GZApplication.getInstance().getDaoSession().getGZbeanDao();
        gzBaseTask = (GZBaseTask) MainBeanFactory.getInstance().newMainInstance("gz");
    }

    private void getData(){
        String lastId = SpUtil.getInstance().getValueFromXml(SpUtil.SP_GZ_LIST_LAST_UPDATE_TIME);
        String requestTime = TextUtils.isEmpty(lastId)?"2015-01-01 00:00:00":lastId;
        queryGZList(requestTime,"");
    }

    private void queryGZList(String requestId,String scanNo){
        Bundle bundle = new Bundle();
        bundle.putString(GZBaseTask.ID,requestId);
        bundle.putSerializable(GZBaseTask.TYPE_ID,"1");
        bundle.putString(GZBaseTask.EVERY_PAGE,"1000000");
        bundle.putString(GZBaseTask.CURRENT_PAGE,"1");
        bundle.putString(GZBaseTask.SCAN_NO,scanNo);
        gzBaseTask.queryBeans(bundle, NetQueryStyle.VOLLEY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getActivity().startActivityForResult(new Intent(getActivity(),
                            MipcaActivityCapture.class),REQUEST_CODE_SCAN);

                } else {
                    ((BaseActivity)getActivity()).showToast(getString(R.string.user_refuse_open_camera));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public static FirstFragment getInstance(Bundle bundle){
        FirstFragment firstFragment = new FirstFragment();
        if(bundle != null){
            firstFragment.setArguments(bundle);
        }
        return firstFragment;
    }

    private AlertDialog dialog;

    @Override
    public void updateGzData(Object object) {
        super.updateGzData(object);

        try{

            if(object instanceof GZBaseTask){
                int method = ((GZBaseTask) object).getExecuteMethod();
                if(method == NetQueryMethod.QUERY_ALL){
                    int resultCode = ((GZBaseTask) object).getResultCode();

                    switch (resultCode){
                        case NetTaskInterface.REQUEST_SUCCESSFUL:
                        {
                            //如果有数据，则说明有数据要更新
                            List ts = ((GZBaseTask) object).getBeans();
                            try{
                                //批量插到库中
                                gZbeanDao.insertOrReplaceInTx(ts);
                            }catch (Exception e){
                                Logger.getLogger().w(e.toString());
                                ((BaseActivity)getActivity()).showToast(getString(R.string.udpate_error));
                                return;
                            }

                            //保存到本地
                            SpUtil.getInstance().insertToxml(SpUtil.SP_GZ_LIST_LAST_UPDATE_TIME,
                                    ((GZBaseTask) object).getBundle().getString("maxtime"));

                            ((BaseActivity)getActivity()).showToast(getString(R.string.update_successfully));
                        }
                        break;
                        default:
                            ((BaseActivity)getActivity()).dismissLoadDialog();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(getString(R.string.tips));
                            builder.setMessage(getString(R.string.no_update_data));
                            builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                            break;
                    }
                }
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }finally {
            ((BaseActivity)getActivity()).dismissLoadDialog();
        }
    }
}
