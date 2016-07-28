package com.hr.nipuream.gz.controller.main.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.activity.GZList;
import com.hr.nipuream.gz.controller.main.activity.GZRepair;
import com.hr.nipuream.gz.zxing.MipcaActivityCapture;

import carbon.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public static final int REQUEST_CODE_SCAN = 0x643;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0x642;

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

        return view;
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
                    ((BaseActivity)getActivity()).showToast("用户拒绝打开摄像头");
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

}
