package com.zzy.business.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Menu;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.common.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PbListAdapter extends
        RecyclerView.Adapter<PbListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<PbRecord> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

/******************************************************************************************************************/
    public PbListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvRemarks,tvPhone;
        private ImageView ivPic;
        private RelativeLayout rlRoot;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvRemarks = view.findViewById(R.id.tvRemarks);
            tvPhone = view.findViewById(R.id.tvPhone);
            ivPic = view.findViewById(R.id.ivPic);
            rlRoot = view.findViewById(R.id.rlRoot);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<PbRecord> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public PbListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_pb_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final PbListAdapter.ViewHolder holder, final int position) {
        try{
            PbRecord bean = mDataSet.get(position);
            holder.tvName.setText(bean.getName());
            holder.tvRemarks.setText(bean.getRemarks());
            holder.tvPhone.setText(bean.getPhone());

            ImageLoader.loadImage(holder.ivPic,bean.getImgUrl());

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
