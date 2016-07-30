package com.hr.nipuream.gz.controller.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.cache.plus.ImageLoader;
import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.controller.main.bean.GZbean;
import com.hr.nipuream.gz.net.Constants;
import com.hr.nipuream.gz.util.Logger;
import java.util.List;
import carbon.widget.CardView;
import carbon.widget.ImageView;
import carbon.widget.TextView;
/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-26 20:08
 * 邮箱：571829491@qq.com
 */
public class GZListAdapter extends RecyclerView.Adapter<GZListAdapter.ViewHolder>{

    private List<GZbean> gZbeanList;

    public static final int GZ_LIST_ITEM_CHOOSE = 0x56;

    public GZListAdapter(List<GZbean> list){
        gZbeanList = list;
    }

    @Override
    public GZListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gz_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try{
            final GZbean bean = gZbeanList.get(position);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GZApplication.getInstance().getEventBus().post(bean);
                }
            });

            holder.gzid.setText("国资编号: "+bean.getSacno());
            holder.zcmc.setText("资产名称: "+bean.getSacname());

            String status = bean.getNowstatus();
            String value = "";
            if(TextUtils.equals(status,"1"))
                value = "未用";
            else
                value = "在用";
            holder.zcxz.setText("国资现状: "+value);

            int sacStatus = bean.getSacstatus();
            String sacValue = "";
            switch (sacStatus){
                case 1:
                    sacValue = "正常";
                    break;
                case 2:
                    sacValue = "维修中";
                    break;
                case 3:
                    sacValue = "报废";
                    break;
            }

            holder.zczt.setText("国资状态: "+sacValue);
            holder.imageView.setImageBitmap(null);

            GZApplication.getInstance().getmImageLoader().get(Constants.BASE_URL + bean.getSacphoto(),
                    ImageLoader.getImageListener(holder.imageView,
                            R.mipmap.aio_image_default,R.mipmap.aio_image_fail),dptopx(100),dptopx(120));

        }catch (Exception e){
            Logger.getLogger().e(e.toString());
        }
    }

    private int dptopx(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,GZApplication.getInstance().getResources().getDisplayMetrics());
    }

    public void setItem(List<GZbean> beans){
        this.gZbeanList = beans;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gZbeanList==null?0:gZbeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  ImageView imageView;
        private TextView gzid,zcmc,zcxz,zczt;
        private CardView cardView;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.gz_list_item_iv);
            gzid = (TextView) view.findViewById(R.id.gz_list_item_zcid);
            zcmc = (TextView)view.findViewById(R.id.gz_list_item_zcmc);
            zcxz = (TextView)view.findViewById(R.id.gz_list_item_zcxz);
            zczt = (TextView)view.findViewById(R.id.gz_list_item_zczt);
            cardView = (CardView) view.findViewById(R.id.gz_list_carview);
        }
    }

}
