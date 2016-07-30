package com.hr.nipuream.gz.controller.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.adapter.GZListAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.controller.main.fragment.SearchGzFragment;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.dao.db.GZbeanDao;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import carbon.widget.ImageView;
import carbon.widget.Toolbar;

/**
 * 显示国资列表
 * @author Nipuream
 */
public class GZList extends BaseActivity implements XRecyclerView.LoadingListener{

    private XRecyclerView recyclerView;
    private GZBaseTask gzBaseTask;

    private int currentPage = 1;

    private GZbeanDao gZbeanDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzlist);
        setViews();
        PopLoadDialog(this);
        getData();
    }

    @Override
    protected void registerGZTask() {
        super.registerGZTask();
        gZbeanDao = GZApplication.getInstance().getDaoSession().getGZbeanDao();
        gzBaseTask = (GZBaseTask) MainBeanFactory.getInstance().newMainInstance("gz");
    }

    private void setViews(){
        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gzmanager));
        recyclerView = (XRecyclerView) findViewById(R.id.gzlist_recyleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ImageView searchIv = (ImageView)findViewById(R.id.gz_list_search_iv);
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
//                bundle.putSerializable(SearchGzFragment.SEARCH_BEANS,(ArrayList)gZbeanLists);
                bundle.putString(SearchGzFragment.WHERE,SearchGzFragment.GZLIST);
                SearchGzFragment dialog = SearchGzFragment.getInstance(bundle);
                dialog.show(GZList.this.getSupportFragmentManager(),"selectGzTag");
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setLoadingListener(this);
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
        bundle.putString(GZBaseTask.EVERY_PAGE,"10");
        bundle.putString(GZBaseTask.CURRENT_PAGE,String.valueOf(currentPage));
        bundle.putString(GZBaseTask.SCAN_NO,scanNo);
        gzBaseTask.queryBeans(bundle, NetQueryStyle.VOLLEY);
    }

    private List<GZbean> offsizeDataFromLocal(int off){
        Query<GZbean> query = gZbeanDao.queryBuilder().orderDesc
                (GZbeanDao.Properties.Id).offset(10*off).limit(10).build();
        return query.list();
    }

    private List<GZbean> gZbeanLists;
    private int pages;
    private GZListAdapter adapter;

    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);

        dismissLoadDialog();
        recyclerView.refreshComplete();
        recyclerView.loadMoreComplete();

        if(object instanceof GZBaseTask){

            int method = ((GZBaseTask) object).getExecuteMethod();
            if(method == NetQueryMethod.QUERY_ALL){

                showToast(((GZBaseTask) object).getResultMessage());


                switch (((GZBaseTask) object).getResultCode()){
                    case NetTaskInterface.REQUEST_SUCCESSFUL:
                    {
                        pages = ((GZBaseTask) object).getPages();

                        try{
                            gZbeanDao.insertOrReplaceInTx(((GZBaseTask) object).getBeans());
                        }catch (Exception e){
                            Logger.getLogger().w(e.toString());
                        }

                        gZbeanLists = offsizeDataFromLocal(currentPage-1);

                        if(adapter == null){
                            adapter = new GZListAdapter(gZbeanLists);
                            recyclerView.setAdapter(adapter);
                        }
                        else
                           adapter.setItem(gZbeanLists);
                        //save local
                        SpUtil.getInstance().insertToxml(SpUtil.SP_GZ_LIST_LAST_UPDATE_TIME,
                                ((GZBaseTask) object).getBundle().getString("maxtime"));
                    }
                    break;
                    default:
                    {
                        //todo 从本地加载
                        gZbeanLists = offsizeDataFromLocal(currentPage - 1);
                        adapter = new GZListAdapter(gZbeanLists);
                        recyclerView.setAdapter(adapter);
                    }
                    break;
                }

            }
        }
        else if(object instanceof GZbean){
            Intent intent = new Intent(this, GZDetail.class);
            intent.putExtra(SearchGzFragment.GO_TO_DETAIL,(GZbean)object);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        try{
            currentPage = 1;
            getData();
        }catch (Exception e){
            Logger.getLogger().w(e.toString());
        }

    }

    @Override
    public void onLoadMore() {
        try{
            if(currentPage < pages){
                if(recyclerView != null){
                    recyclerView.setLoadingMoreEnabled(true);
                }
                currentPage++;
                offsizeDataFromLocal(currentPage-1);
            }else{
                if(recyclerView != null){
                    recyclerView.setLoadingMoreEnabled(false);
                }
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }finally {
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }
    }
}
