package com.zzy.business.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.business.model.bean.Goods;
import com.zzy.common.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends
        RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<Goods> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

/******************************************************************************************************************/
    public GoodsListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvPrice,tvPhone,tvContact;
        private ImageView ivPic;
        private RelativeLayout rlRoot;
        private RatingBar rbScore;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvPhone = view.findViewById(R.id.tvPhone);
            tvContact = view.findViewById(R.id.tvContact);
            ivPic = view.findViewById(R.id.ivPic);
            rlRoot = view.findViewById(R.id.rlRoot);
            rbScore = view.findViewById(R.id.rbScore);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<Goods> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_goods_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final GoodsListAdapter.ViewHolder holder, final int position) {
        try{
            Goods bean = mDataSet.get(position);
            holder.tvName.setText(bean.getName());
            holder.tvPhone.setText(bean.getPhone());
            holder.tvContact.setText(bean.getContact());

            if(bean.getScore()>0){
                //卖
                holder.rbScore.setVisibility(View.VISIBLE);
                holder.rbScore.setStar(bean.getScore());
                holder.tvPrice.setText("¥"+bean.getPrice());
            }else{
                //买
                holder.tvPrice.setText("¥"+bean.getStartPrice()+"-"+bean.getEndPrice());
            }
            ImageLoader.loadImage(holder.ivPic,bean.getImgUrlList().get(0));

            holder.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClicked(position);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mDataSet == null?0:mDataSet.size();
    }
}
