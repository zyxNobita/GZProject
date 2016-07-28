package com.hr.nipuream.gz.controller.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import carbon.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class GZRepairList extends BaseFragment implements XRecyclerView.LoadingListener{

    private XRecyclerView recyclerView;


    public GZRepairList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_gzrepair_list, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gz_repair));
        recyclerView = (XRecyclerView) view.findViewById(R.id.gz_repair_recyleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setLoadingListener(this);
        return view;
    }

    public static GZRepairList getInstance(Bundle bundle){
        GZRepairList gzRepairList = new GZRepairList();
        if(bundle != null){
            gzRepairList.setArguments(bundle);
        }
        return gzRepairList;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
