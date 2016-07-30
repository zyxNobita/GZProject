package com.hr.nipuream.gz.controller.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.base.BaseFragment;
import com.hr.nipuream.gz.controller.main.adapter.GZRepairAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZRepairbean;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZRepairBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GZRepairList extends BaseFragment implements XRecyclerView.LoadingListener{

//    private XRecyclerView recyclerView;

    public GZRepairList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_gzrepair_list, container, false);
//        recyclerView = (XRecyclerView) view.findViewById(R.id.gz_repair_recyleview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        recyclerView.setLoadingListener(this);

        adapter = new GZRepairAdapter(null);
//        recyclerView.setAdapter(adapter);
        ((BaseActivity)this.getActivity()).PopLoadDialog(getActivity());
        runTask(null);
        return view;
    }


    private GZRepairBaseTask gzRepairBaseTask;

    @Override
    public void registerTask() {
        super.registerTask();
        gzRepairBaseTask = (GZRepairBaseTask) MainBeanFactory.
                getInstance().newMainInstance("gzpair");
    }

    private int currentPage = 1;
    private int pages = 1;

    private void runTask(String scano){
        try{
            Bundle bundle = new Bundle();

            String userId = SpUtil.getInstance().
                    getValueFromXml(SpUtil.SP_GZ_USER_ID);
            if(!TextUtils.isEmpty(userId))
                bundle.putString(GZRepairBaseTask.USER_ID,userId);

            if(!TextUtils.isEmpty(scano))
                bundle.putString(GZRepairBaseTask.SCAN_NO,scano);

            bundle.putString(GZRepairBaseTask.CURRENTPAGE,String.valueOf(currentPage));
            bundle.putString(GZRepairBaseTask.EVERYPAGE,"10");

            gzRepairBaseTask.queryBeans(bundle, NetQueryStyle.VOLLEY);
        }catch (Exception e){
            Logger.getLogger().w(e.toString());
        }
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
        currentPage = 1;
//        recyclerView.setLoadingMoreEnabled(true);
        runTask(null);
    }

    @Override
    public void onLoadMore() {
//        if(currentPage < pages){
//            currentPage ++;
//            runTask(null);
//        }
//        else
//            recyclerView.setLoadingMoreEnabled(false);
    }

    private GZRepairAdapter adapter;
    private List<GZRepairbean> beans = new ArrayList<>();

    @Override
    public void updateGzData(Object object) {
        super.updateGzData(object);
        try{

            if(object instanceof GZRepairBaseTask){
                int method = ((GZRepairBaseTask) object).getExecuteMethod();
                if(method == NetQueryMethod.QUERY_ALL){

                    int resultCode = ((GZRepairBaseTask) object).getResultCode();
                    switch (resultCode){
                        case NetTaskInterface.REQUEST_SUCCESSFUL:
                        {

                            pages = ((GZRepairBaseTask) object).getPages();
                            List<GZRepairbean> beans = ((GZRepairBaseTask) object).getBeans();

//                            if(recyclerView.isLoadMore())
//                                this.beans.addAll(beans);
//                            else
//                                this.beans = beans;
//
//                            adapter.setItem(this.beans);

                        }
                        break;
                        case NetTaskInterface.REQUEST_EMPTY:
                        {

                        }
                        break;
                        default:
                        {

                        }
                        break;
                    }

                }

            }

        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }finally {
            ((BaseActivity)this.getActivity()).dismissLoadDialog();
//            recyclerView.refreshComplete();
//            recyclerView.loadMoreComplete();
        }
    }
}
