package com.hr.nipuream.gz.controller.setting.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.gz.R;

import carbon.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.setting));
        return view;
    }


    public static SettingFragment getInstance(Bundle bundle){
        SettingFragment fragment = new SettingFragment();
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

}
