package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class GoodsDelegate implements ItemViewDelegate<Goods> {
    private TextView tvName,tvPrice,tvPhone,tvContact;
    private ImageView ivPic;
    private RatingBar rbScore;
    private int type;

    public GoodsDelegate(int type) {
        this.type = type;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_goods_list_item;
    }

    @Override
    public boolean isForViewType(Goods item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Goods bean, final int position) {
        try{
            tvName = holder.itemView.findViewById(R.id.tvName);
            tvPrice = holder.itemView.findViewById(R.id.tvPrice);
            tvPhone = holder.itemView.findViewById(R.id.tvPhone);
            tvContact = holder.itemView.findViewById(R.id.tvContact);
            ivPic = holder.itemView.findViewById(R.id.ivPic);
            rbScore = holder.itemView.findViewById(R.id.rbScore);

            tvName.setText(bean.getName());
            tvPhone.setText(bean.getPhone());
            tvContact.setText(bean.getContact());

            rbScore.setmClickable(false);
            if(type == CommonConstants.GOODS_SELL
                ||type == CommonConstants.MY_GOODS_SELL
            ){
                //卖
                rbScore.setVisibility(View.VISIBLE);
                rbScore.setStar(bean.getScore());
                tvPrice.setText("¥"+bean.getPrice());
            }else{
                //买
                tvPrice.setText("¥"+bean.getStartPrice()+"-"+bean.getEndPrice());
            }
            ImageLoader.loadImage(ivPic,bean.getImgList().get(0).getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
