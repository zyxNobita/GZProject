package com.hr.nipuream.gz.controller.main.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.activity.GZDetail;
import com.hr.nipuream.gz.controller.main.adapter.SearchGzAdapter;
import com.hr.nipuream.gz.controller.main.adapter.SearchGzRepairAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZRepairbean;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.controller.main.task.MainBeanFactory;
import com.hr.nipuream.gz.controller.main.task.net.GZRepairBaseTask;
import com.hr.nipuream.gz.dao.SpUtil;
import com.hr.nipuream.gz.dao.db.GZbeanDao;
import com.hr.nipuream.gz.net.NetQueryMethod;
import com.hr.nipuream.gz.net.NetQueryStyle;
import com.hr.nipuream.gz.net.NetTaskInterface;
import com.hr.nipuream.gz.util.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGzFragment extends DialogFragment
        implements TextWatcher,SearchGzAdapter.SelectItemListener,
        SearchGzRepairAdapter.SelectItemListener{

//    public static final String SEARCH_BEANS = "search.beans";
//    private ArrayList<GZbean> beans;

    public static final String WHERE = "where";
    public static final String GZLIST = "GZLIST";
    public static final String GZREPAIR = "GZREPAIR";

    private String where ;

    public SearchGzFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.loaddialog);
        try{
            Bundle data = getArguments();
//            beans = (ArrayList<GZbean>) data.getSerializable(SEARCH_BEANS);
            where = data.getString(WHERE);
        }catch (Exception e){
            Logger.getLogger().w(e.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setGravity(Gravity.TOP);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private EditText searchEt;
    private ListView listView;
    private SearchGzAdapter adapter;
    private SearchGzRepairAdapter adapter1;
    private GZbeanDao dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_gz, container, false);
        searchEt = (EditText) view.findViewById(R.id.search);
        searchEt.addTextChangedListener(this);
        ImageView closeBtn = (ImageView)view.findViewById(R.id.search_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        listView = (ListView) view.findViewById(R.id.search_gz_lv);

        if(TextUtils.equals(where,GZLIST)){
            dao = GZApplication.getInstance().getDaoSession().getGZbeanDao();
            adapter = new SearchGzAdapter(new ArrayList<GZbean>());
            adapter.setOnSelectItemListener(this);
            listView.setAdapter(adapter);
        }
        else if(TextUtils.equals(where,GZREPAIR)){
            GZApplication.getInstance().
                    getEventBus().register(this);
            registerTask();
            adapter1 = new SearchGzRepairAdapter(new ArrayList<GZRepairbean>());
            adapter1.setOnSelectItemListener(this);
            listView.setAdapter(adapter1);
        }
        return view;
    }

    private GZRepairBaseTask gzRepairBaseTask;

    private void registerTask(){
        gzRepairBaseTask = (GZRepairBaseTask) MainBeanFactory.
                getInstance().newMainInstance("gzpair");
    }

    private int currentPage = 1;

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

    public static SearchGzFragment getInstance(Bundle bundle){
        SearchGzFragment gzFragment = new SearchGzFragment();
        if(bundle != null){
            gzFragment.setArguments(bundle);
        }
        return gzFragment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String result = s.toString();


        if(TextUtils.equals(where,GZLIST))
        {
            List<GZbean> list = new ArrayList<>();
            if(!TextUtils.isEmpty(result)){
                list = dao.queryBuilder().where(GZbeanDao.Properties.Sacno.like("%" + result + "%")).build().list();
            }
            adapter.setBeans(list);
        }
        else if(TextUtils.equals(where,GZREPAIR)){
            if(TextUtils.isEmpty(result)){
                adapter1.setItems(new ArrayList<GZRepairbean>());
                return;
            }
            runTask(result);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(Object object){

        try{

            if(object instanceof GZRepairBaseTask){
                int method = ((GZRepairBaseTask) object).getExecuteMethod();
                if(method == NetQueryMethod.QUERY_ALL){
                    int result = ((GZRepairBaseTask) object).getResultCode();
                    switch (result){
                        case NetTaskInterface.REQUEST_SUCCESSFUL:
                        {
                            List beans = ((GZRepairBaseTask) object).getBeans();
                            adapter1.setItems(beans);
                        }
                            break;
                        default:
                            adapter1.setItems(new ArrayList<GZRepairbean>());
                            break;
                    }
                }
            }

        }catch (Exception e){

        }

    }


    public static final String GO_TO_DETAIL = "search.gz.fragment";

    @Override
    public void select(int pos, GZbean gZbean) {
        Intent intent = new Intent(getActivity(), GZDetail.class);
        intent.putExtra(GO_TO_DETAIL,gZbean);
        getActivity().startActivity(intent);
        this.dismiss();
    }


    @Override
    public void onDetach() {
        try{
            adapter.setOnSelectItemListener(null);
            searchEt.addTextChangedListener(null);
            GZApplication.getInstance().getEventBus().unregister(this);
        }catch (Exception e){}
        super.onDetach();
    }


    public static final int SEND_GZ_REPAIR_SELECT_BEAN = 0x75;

    @Override
    public void select(int pos, GZRepairbean gZbean) {
        BaseActivity.getMyHandler().obtainMessage(SEND_GZ_REPAIR_SELECT_BEAN,gZbean).sendToTarget();
    }

}
