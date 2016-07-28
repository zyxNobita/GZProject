package com.hr.nipuream.gz.controller.manager.fragment;


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
public class GzManagerFragment extends Fragment {


    public GzManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_gz_manager, container, false);
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gzmanager));
        return view;
    }


    public static GzManagerFragment getInstance(Bundle bundle){
        GzManagerFragment gzManagerFragment = new GzManagerFragment();
        if(bundle != null){
            gzManagerFragment.setArguments(bundle);
        }
        return gzManagerFragment;
    }


}
