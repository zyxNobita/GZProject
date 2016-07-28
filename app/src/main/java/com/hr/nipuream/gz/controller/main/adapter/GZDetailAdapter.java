package com.hr.nipuream.gz.controller.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.gz.R;

import java.util.List;

import carbon.widget.RecyclerView;
import carbon.widget.TextView;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-24 14:05
 * 邮箱：571829491@qq.com
 */
public class GZDetailAdapter extends RecyclerView.ListAdapter<GZDetailAdapter.ViewHolder,String>{

    public GZDetailAdapter(List<String> fruits) {
        super(fruits);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gz_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
            String str =getItem(position);
            String[] values = str.split(",");
            holder.name.setText(values[0]);
            holder.value.setText(values[1]);
        }catch (Exception e){}
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,value;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.gz_detail_item_name);
            value = (TextView) itemView.findViewById(R.id.gz_detail_item_value);
        }
    }

}
