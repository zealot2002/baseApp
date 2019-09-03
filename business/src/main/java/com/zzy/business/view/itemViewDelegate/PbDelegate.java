package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class PbDelegate implements ItemViewDelegate<PbRecord> {
    private TextView tvName,tvRemarks,tvPhone;
    private ImageView ivPic;
    private Context context;

    public PbDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_pb_list_item;
    }

    @Override
    public boolean isForViewType(PbRecord item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, PbRecord bean, final int position) {
        try{
            tvName = holder.itemView.findViewById(R.id.tvName);
            tvRemarks = holder.itemView.findViewById(R.id.tvRemarks);
            tvPhone = holder.itemView.findViewById(R.id.tvPhone);
            ivPic = holder.itemView.findViewById(R.id.ivPic);

            tvName.setText(bean.getName());
            tvRemarks.setText(bean.getRemarks());
            tvPhone.setText(bean.getPhone());

            ImageLoader.loadImage(ivPic,bean.getImgUrl());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
