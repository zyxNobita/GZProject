package com.hr.nipuream.gz.controller.main.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.base.BaseActivity;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.util.Logger;

import java.util.List;

import carbon.widget.CardView;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-27 14:11
 * 邮箱：571829491@qq.com
 */
public class SearchGzAdapter extends BaseAdapter{

    private List<GZbean> beans;

    public SearchGzAdapter(List<GZbean> beans){
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans == null ?0:beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans == null?null:beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setBeans(List<GZbean> beans){
        this.beans = beans;
        this.notifyDataSetChanged();
    }

    public interface SelectItemListener{
        void select(int pos,GZbean gZbean);
    }

    private SelectItemListener l;

    public void setOnSelectItemListener(SelectItemListener l){
        this.l = l;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_gz_item,parent,false);
        }

        CardView cardView = BaseActivity.get(convertView,R.id.search_gz_cardview);
        TextView name = BaseActivity.get(convertView,R.id.search_item_name);
        TextView value = BaseActivity.get(convertView,R.id.search_item_value);

        final GZbean gZbean = beans.get(position);

        try{
            name.setText("资产名称");
            String scanName = gZbean.getSacname();
            if(!TextUtils.isEmpty(scanName))
                value.setText(scanName);
        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l != null)
                    l.select(position,gZbean);
            }
        });

        return convertView;
    }
}
