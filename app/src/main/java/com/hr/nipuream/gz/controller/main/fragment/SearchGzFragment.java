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
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.controller.main.activity.GZDetail;
import com.hr.nipuream.gz.controller.main.adapter.SearchGzAdapter;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.util.Logger;
import java.util.ArrayList;
import java.util.List;
import carbon.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGzFragment extends DialogFragment
        implements TextWatcher,SearchGzAdapter.SelectItemListener{

    public static final String SEARCH_BEANS = "search.beans";
    private ArrayList<GZbean> beans;

    public SearchGzFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.loaddialog);
        try{

            Bundle data = getArguments();
            beans = (ArrayList<GZbean>) data.getSerializable(SEARCH_BEANS);

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

        adapter = new SearchGzAdapter(new ArrayList<GZbean>());
        adapter.setOnSelectItemListener(this);
        listView.setAdapter(adapter);

        return view;
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
        List<GZbean> indexBeans = new ArrayList<>();
        String  result =  s.toString().toUpperCase();
        for(GZbean gZbean:beans){
            String sacno = gZbean.getSacno().toUpperCase();
            if(TextUtils.indexOf(sacno,result)>=0)
                 indexBeans.add(gZbean);
        }
       adapter.setBeans(indexBeans);
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
        }catch (Exception e){}
        super.onDetach();
    }
}
