package com.hr.nipuream.gz.controller.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.adapter.GZRepairAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZRepairbean;
import com.hr.nipuream.gz.controller.main.fragment.SearchGzFragment;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZRepairBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.util.ArrayList;
import java.util.List;
import carbon.widget.ImageView;
import carbon.widget.Toolbar;

public class GZRepair extends BaseActivity implements
        XRecyclerView.LoadingListener{

    private Toolbar toolbar;
    private XRecyclerView recyclerView;
    private GZRepairAdapter adapter;
    private GZRepairBaseTask gzRepairBaseTask;
    private ImageView searchIv,plusIv;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try{

            if(msg.what == SearchGzFragment.SEND_GZ_REPAIR_SELECT_BEAN){
                dialog.dismiss();
                GZRepairbean gzRepairbean = (GZRepairbean) msg.obj;
                List<GZRepairbean> beans = new ArrayList<>();
                beans.add(gzRepairbean);
                adapter.setItem(beans);
            }

        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzrepair);
        setViews();
        PopLoadDialog(this);
        runTask(null);
    }

    private void setViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gz_repair));
        recyclerView = (XRecyclerView) findViewById(R.id.gz_repair_recyleview);

        searchIv = (ImageView)findViewById(R.id.gz_repair_list_search_iv);
        plusIv = (ImageView)findViewById(R.id.gz_repair_list_plus_iv);
        searchIv.setOnClickListener(this);
        plusIv.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setLoadingListener(this);
        adapter = new GZRepairAdapter(null);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void registerTask() {
        super.registerTask();
        gzRepairBaseTask = (GZRepairBaseTask) MainBeanFactory.
                getInstance().newMainInstance("gzpair");
    }

    private static final int ADD_REPAIR_REQUEST_CODE = 0x677;

    private SearchGzFragment dialog;

    @Override
    public void clickView(View view) {
        super.clickView(view);
        switch (view.getId()){
            case R.id.gz_repair_list_search_iv:
            {
                //搜索
                Bundle bundle = new Bundle();
                bundle.putString(SearchGzFragment.WHERE,SearchGzFragment.GZREPAIR);
                dialog = SearchGzFragment.getInstance(bundle);
                dialog.show(getSupportFragmentManager(),"selectGzRepairTag");
            }
            break;
            case R.id.gz_repair_list_plus_iv:
            {
                //添加国资维修
                startActivityForResult(new Intent(this,AddGZRepair.class),ADD_REPAIR_REQUEST_CODE);
            }
            break;
        }

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

    private boolean isCallBack = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(requestCode == ADD_REPAIR_REQUEST_CODE
                    && resultCode == Activity.RESULT_OK){
                isCallBack = true;
                runTask(null);
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        recyclerView.setLoadingMoreEnabled(true);
        runTask(null);
    }

    @Override
    public void onLoadMore() {
        if(currentPage < pages){
            currentPage ++;
            runTask(null);
        }
        else
            recyclerView.setLoadingMoreEnabled(false);
    }


    private List<GZRepairbean> beans = new ArrayList<>();

    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);

        if(dialog != null && dialog.isVisible())
            return;

        try{
            if(object instanceof GZRepairBaseTask){
                int method = ((GZRepairBaseTask) object).getExecuteMethod();
                if(method == NetQueryMethod.QUERY_ALL){

                    String message = ((GZRepairBaseTask) object).getResultMessage();
                    if(!TextUtils.isEmpty(message))
                        showToast(message);

                    int resultCode = ((GZRepairBaseTask) object).getResultCode();
                    switch (resultCode){
                        case NetTaskInterface.REQUEST_SUCCESSFUL:
                        {
                            pages = ((GZRepairBaseTask) object).getPages();
                            List<GZRepairbean> beans = ((GZRepairBaseTask) object).getBeans();

                            if(currentPage == pages)
                                recyclerView.setLoadingMoreEnabled(false);

                            if(recyclerView.isRersh()||isCallBack)
                                this.beans = beans;
                            else {
                                this.beans.addAll(beans);
                            }

                            adapter.setItem(this.beans);

                        }
                        break;
                        case NetTaskInterface.REQUEST_EMPTY:
                            showToast(getString(R.string.gz_no_data));
                            break;
                        default:
                            break;
                    }
                }
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }finally {
            isCallBack = false;
            dismissLoadDialog();
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }
    }
}
