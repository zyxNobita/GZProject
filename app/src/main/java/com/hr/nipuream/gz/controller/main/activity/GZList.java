package com.hr.nipuream.gz.controller.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.adapter.GZListAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.controller.main.fragment.SearchGzFragment;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.dao.db.GZbeanDao;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetUtil;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.util.QueryOffLineData;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import org.greenrobot.greendao.query.Query;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import carbon.widget.ImageView;
import carbon.widget.Toolbar;

/**
 * 显示国资列表
 * @author Nipuream
 */
public class GZList extends BaseActivity implements XRecyclerView.LoadingListener{

    private XRecyclerView recyclerView;
//    private GZBaseTask gzBaseTask;

    /**
     * 当前的页数
     */
    private int currentPage = 1;

    /**
     * 数据库操作对象
     */
    private GZbeanDao gZbeanDao;

    private AlertDialog dialog;


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try{
            if(msg.what == NetUtil.NET_UTIL_PROGRESS_UPDATE){
                int progress = (int) msg.obj;
                setDownLineDialogProgress(progress);
            }else if(msg.what == NetUtil.NET_UTIL_EXECUTE_COMPELETE){
                getMyHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeDownLineDialog();
                    }
                },500);
            }else if(msg.what == NetUtil.NET_UTIL_EXECUTE_ERROR){
                getMyHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeDownLineDialog();
                    }
                },500);
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzlist);
        setViews();
//      getData();
        queryDataForLocal();
    }

    private void queryDataForLocal(){
        try{
            int count = (int) gZbeanDao.queryBuilder().count();

            if(count > 0)
            {

                pages = (count % Constants.EVERY_PAGE_ITEMS == 0)?
                        count/Constants.EVERY_PAGE_ITEMS:count/Constants.EVERY_PAGE_ITEMS+1;

                //第一次进来
                gZbeanLists.addAll(offsizeDataFromLocal(currentPage - 1));
                refreshDatas(gZbeanLists);

            }else
            {
                //提示用户下载离线数据
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.tips));
                builder.setMessage(getString(R.string.update_offline_data));
                builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PopDownLoadOffLineData();

                        ExecutorService exec = Executors.newCachedThreadPool();
                        try {
                            Future<String> execResult = exec.submit(new QueryOffLineData());
                            String requestData = execResult.get();

                            JSONObject jsonObject = new JSONObject(requestData);

                            String resultMessage = jsonObject.optString("message");
                            String maxTime = jsonObject.optString("maxtime");
                            if(!TextUtils.isEmpty(maxTime))
                                SpUtil.getInstance().insertToxml(SpUtil.SP_GZ_LIST_LAST_UPDATE_TIME,maxTime);

                            if(!TextUtils.isEmpty(resultMessage))
                                showToast(resultMessage);
                            if(Integer.parseInt(jsonObject.optString("status"))==0){
                                List ts = new Gson().fromJson(jsonObject.optJSONObject("data").optJSONArray("result").toString(),new TypeToken<List<GZbean>>(){}.getType());
                                try{
                                    //批量插到库中
                                    gZbeanDao.insertOrReplaceInTx(ts);
                                }catch (Exception e){
                                    Logger.getLogger().w(e.toString());
                                }
                            }

                            gZbeanLists.clear();
                            gZbeanLists.addAll(offsizeDataFromLocal(currentPage-1));

                            refreshDatas(gZbeanLists);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            exec.shutdown();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }


    @Override
    protected void registerGZTask() {
        super.registerGZTask();
        gZbeanDao = GZApplication.getInstance().getDaoSession().getGZbeanDao();
//        gzBaseTask = (GZBaseTask) MainBeanFactory.getInstance().newMainInstance("gz");
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
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(this);
    }


    private void refreshDatas(List<GZbean> gZbeanLists){
        if(adapter == null){
            adapter = new GZListAdapter(gZbeanLists);
            recyclerView.setAdapter(adapter);
        }
        else
            adapter.setItem(gZbeanLists);
    }


    private List<GZbean> offsizeDataFromLocal(int off){
        Query<GZbean> query = gZbeanDao.queryBuilder().orderDesc
                (GZbeanDao.Properties.Id).offset(Constants.EVERY_PAGE_ITEMS*off).
                limit(Constants.EVERY_PAGE_ITEMS).build();
        return query.list();
    }

    private List<GZbean> gZbeanLists = new ArrayList<>();
    private int pages;
    private GZListAdapter adapter;



    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);
        if(object instanceof GZbean){
            Intent intent = new Intent(this, GZDetail.class);
            intent.putExtra(SearchGzFragment.GO_TO_DETAIL,(GZbean)object);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        //修改成只从本地加载
        try{
            if(currentPage < pages){

                if(recyclerView != null) recyclerView.setLoadingMoreEnabled(true);
                currentPage ++;
                gZbeanLists.addAll(offsizeDataFromLocal(currentPage-1));
                refreshDatas(gZbeanLists);

            }else{
                if(recyclerView != null) recyclerView.setLoadingMoreEnabled(false);
            }
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }finally {
            recyclerView.loadMoreComplete();
        }
    }
}
