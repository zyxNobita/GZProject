package com.hr.nipuream.gz.controller.main.activity;

import android.os.Bundle;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.fragment.GZRepairList;

public class GZRepair extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzrepair);
        enterRepairList();
    }

    private void enterRepairList(){
        GZRepairList fragment = GZRepairList.getInstance(null);
        addFragment(fragment,true,R.id.gz_repair_container,true);
    }





}
