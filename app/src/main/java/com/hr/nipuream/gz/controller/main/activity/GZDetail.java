package com.hr.nipuream.gz.controller.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.android.volley.cache.plus.ImageLoader;
import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.adapter.GZDetailAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.controller.main.fragment.SearchGzFragment;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZBaseTask;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.util.Logger;
import java.util.ArrayList;
import java.util.List;
import carbon.widget.RecyclerView;
import carbon.widget.Toolbar;

public class GZDetail extends BaseActivity {

    private GZBaseTask gzBaseTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzdetail);

        GZbean gZbean = (GZbean) getIntent().
                getSerializableExtra(SearchGzFragment.GO_TO_DETAIL);

        String code = getIntent().getStringExtra("code");
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.gz_detail));
        recyclerView = (RecyclerView) findViewById(R.id.gzdetail_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(gZbean == null)
        {
            PopLoadDialog(this);
            queryDetail(code);
        }
        else
            initValuesToViews(gZbean);
    }


    @Override
    protected void registerGZTask() {
        super.registerGZTask();
        gzBaseTask = (GZBaseTask) MainBeanFactory.getInstance().newMainInstance("gz");
    }

    private void queryDetail(String code){
        Bundle bundle = new Bundle();
        bundle.putString(GZBaseTask.SCAN_NO,code);
        bundle.putString(GZBaseTask.TYPE_ID,"1");
        gzBaseTask.queryOneBean(bundle,NetQueryStyle.VOLLEY);
    }


    private List<String> createList(GZbean gZbean){

        List<String> lists = new ArrayList<>();
        if(!TextUtils.isEmpty(gZbean.getSacno()))lists.add("资产编号"+","+gZbean.getSacno());
        if(!TextUtils.isEmpty(gZbean.getSacname()))lists.add("资产名称"+","+gZbean.getSacname());
        if(!TextUtils.isEmpty(gZbean.getTypename()+""))lists.add("国资分类"+","+gZbean.getTypename());
        if(!TextUtils.isEmpty(gZbean.getUnitprice()+""))lists.add("单价"+","+gZbean.getUnitprice());
        if(!TextUtils.isEmpty(gZbean.getTotalprice()+""))lists.add("总价"+","+gZbean.getTotalprice());
        if(!TextUtils.isEmpty(gZbean.getSacnumber()+""))lists.add("数量"+","+gZbean.getSacnumber());
        if(!TextUtils.isEmpty(gZbean.getUnit()))lists.add("计量单位"+","+gZbean.getUnit());
        if(!TextUtils.isEmpty(gZbean.getUsepeople()))lists.add("使用人"+","+gZbean.getUsepeople());
        if(!TextUtils.isEmpty(gZbean.getDeptname()+""))lists.add("管理部门"+","+gZbean.getDeptname());
        if(!TextUtils.isEmpty(gZbean.getStoragelocation()))lists.add("存放地点"+","+gZbean.getStoragelocation());
        if(!TextUtils.isEmpty(gZbean.getNowstatus()))
        {
            String value = "";
            if(TextUtils.equals(gZbean.getNowstatus(),"1"))
                value = "未用";
            else if(TextUtils.equals(gZbean.getNowstatus(),"2"))
                value = "在用";
            lists.add("现状"+","+value);
        }

        if(!TextUtils.isEmpty(gZbean.getSacstatus()+""))
        {
            String value = "";
            if(TextUtils.equals(gZbean.getSacstatus()+"","1"))
                value = "正常";
            else if(TextUtils.equals(gZbean.getSacstatus()+"","2"))
                value = "维修中";
            else if(TextUtils.equals(gZbean.getSacstatus()+"","3"))
                value = "报废";
            lists.add("资产状态"+","+value);
        }

        if(!TextUtils.isEmpty(gZbean.getMacname()))lists.add("生产厂家"+","+gZbean.getMacname());
        if(!TextUtils.isEmpty(gZbean.getFactorynumber()))lists.add("出厂编号"+","+gZbean.getFactorynumber());
        if(!TextUtils.isEmpty(gZbean.getSacmodel()))lists.add("型号"+","+gZbean.getSacmodel());
        if(!TextUtils.isEmpty(gZbean.getSacspec()))lists.add("规格"+","+gZbean.getSacspec());
        if(!TextUtils.isEmpty(gZbean.getTechstaus()+""))
        {
            String value = "";
            if(TextUtils.equals(gZbean.getTechstaus()+"","1"))
                value = "未审核";
            else if(TextUtils.equals(gZbean.getTechstaus()+"","2"))
                value = "已审核";
            lists.add("归口审核状态"+","+value);
        }
        if(!TextUtils.isEmpty(gZbean.getTechperson()))lists.add("归口审核人"+","+gZbean.getTechperson());
        if(!TextUtils.isEmpty(gZbean.getTechcompany()))lists.add("归口单位"+","+gZbean.getTechcompany());
        if(!TextUtils.isEmpty(gZbean.getTechdate()))lists.add("归口审核日期"+","+gZbean.getTechdate());
        if(!TextUtils.isEmpty(gZbean.getTechopinion()))lists.add("归口审核意见"+","+gZbean.getTechopinion());
        if(!TextUtils.isEmpty(gZbean.getFastaus()+""))
        {
            String value = "";

            if(TextUtils.equals(gZbean.getFastaus()+"","1"))
                value = "未审核";
            else if(TextUtils.equals(gZbean.getFastaus()+"","2"))
                value = "已审核";

            lists.add("财务审核状态"+","+value);
        }
        if(!TextUtils.isEmpty(gZbean.getFaperson()))lists.add("财务审核人"+","+gZbean.getFaperson());
        if(!TextUtils.isEmpty(gZbean.getFadate()))lists.add("财务审核日期"+","+gZbean.getFadate());
        if(!TextUtils.isEmpty(gZbean.getFaopinion()))lists.add("财务审核意见"+","+gZbean.getFaopinion());
        if(!TextUtils.isEmpty(gZbean.getRemark()))lists.add("资产备注"+","+gZbean.getRemark());
        return lists;
    }


    @Override
    public void updateGZdata(Object object) {
        super.updateGZdata(object);
        dismissLoadDialog();
        if(object instanceof GZBaseTask){
            showToast(((GZBaseTask) object).getResultMessage());
            if(((GZBaseTask) object).getExecuteMethod() == NetQueryMethod.QUERY){
                GZbean t = (GZbean) ((GZBaseTask) object).getBean();
                initValuesToViews(t);
            }
        }
    }


    private void initValuesToViews(GZbean t){
        GZDetailAdapter adapter = new GZDetailAdapter(createList(t));
        recyclerView.setAdapter(adapter);

        View view = getLayoutInflater().inflate(R.layout.gz_detail_layout_header,null);
        ImageView iv = (ImageView) view.findViewById(R.id.gz_detail_header);

        Logger.getLogger().e(Constants.BASE_URL+t.getSacphoto());
        GZApplication.getInstance().getmImageLoader().get(Constants.BASE_URL+t.getSacphoto(),
                ImageLoader.getImageListener(iv,R.mipmap.aio_image_default,R.mipmap.aio_image_fail),1024,768);
        recyclerView.setHeader(view);
    }

}
