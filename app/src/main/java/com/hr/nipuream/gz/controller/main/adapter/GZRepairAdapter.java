package com.hr.nipuream.gz.controller.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.controller.main.bean.GZRepairbean;
import com.hr.nipuream.gz.util.Logger;
import java.util.List;
import carbon.widget.CardView;
import carbon.widget.TextView;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-29 20:58
 * 邮箱：571829491@qq.com
 */
public class GZRepairAdapter extends RecyclerView.Adapter<GZRepairAdapter.ViewHolder>{

    private List<GZRepairbean> gzRepairbeanList;

    public GZRepairAdapter(List<GZRepairbean> gzRepairbeanList){
        this.gzRepairbeanList = gzRepairbeanList;
    }

    @Override
    public GZRepairAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gz_repair_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try{

            final GZRepairbean gzRepairbean = gzRepairbeanList.get(position);

            if(!TextUtils.isEmpty(gzRepairbean.getCreatetime()))
                holder.createTime.setText("创建时间: "+gzRepairbean.getCreatetime());
            if(!TextUtils.isEmpty(gzRepairbean.getCompletiontime())){
                holder.fixCompleTime.setVisibility(View.VISIBLE);
                holder.fixCompleTime.setText("维修完成时间: "+gzRepairbean.getCompletiontime());
            }
            if(!TextUtils.isEmpty(gzRepairbean.getEctime())){
                holder.preFixTime.setVisibility(View.VISIBLE);
                holder.preFixTime.setText("预计完成维修时间: "+gzRepairbean.getEctime());
            }
            if(!TextUtils.isEmpty(gzRepairbean.getMaintainman()))
            {
                holder.fixPerson.setVisibility(View.VISIBLE);
                holder.fixPerson.setText("维修人: "+gzRepairbean.getMaintainman());
            }
            holder.fixStatus.setText(gzRepairbean.getStaus()==1?"维修中":"维修完成");
            if(!TextUtils.isEmpty(gzRepairbean.getMaintaintime()))
            {
                holder.fixTime.setVisibility(View.VISIBLE);
                holder.fixTime.setText("维修时间: "+gzRepairbean.getMaintaintime());
            }
            if(!TextUtils.isEmpty(gzRepairbean.getProblemdesc()))
            {
                holder.Prodesc.setVisibility(View.VISIBLE);
                holder.Prodesc.setText("问题描述: "+gzRepairbean.getProblemdesc());
            }
            if(!TextUtils.isEmpty(gzRepairbean.getSacname()))
                holder.gzName.setText(gzRepairbean.getSacname());


        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return gzRepairbeanList == null ? 0:gzRepairbeanList.size();
    }


    public void setItem(List<GZRepairbean> beans){
        this.gzRepairbeanList = beans;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gzName,fixStatus,Prodesc,fixPerson,
                fixTime,preFixTime,fixCompleTime,createTime;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            gzName = (TextView) view.findViewById(R.id.gz_repair_list_name);
            fixStatus = (TextView)view.findViewById(R.id.gz_repair_status);
            Prodesc = (TextView)view.findViewById(R.id.gz_repair_list_item_problem);
            fixPerson = (TextView)view.findViewById(R.id.gz_repair_list_item_people);
            fixTime = (TextView)view.findViewById(R.id.gz_repair_list_item_time);
            preFixTime = (TextView)view.findViewById(R.id.gz_repair_list_item_pre_time);
            fixCompleTime = (TextView)view.findViewById(R.id.gz_repair_list_item_complete_time);
            createTime = (TextView)view.findViewById(R.id.gz_repair_list_item_create_time);
            cardView = (CardView)view.findViewById(R.id.gz_repair_list_carview);
        }
    }

}
