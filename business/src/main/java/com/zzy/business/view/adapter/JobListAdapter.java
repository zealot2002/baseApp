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
import com.zzy.business.model.bean.Jd;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends
        RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<Jd> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

    /******************************************************************************************************************/
    public JobListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate,tvFrom;
        private ImageView ivPic;
        private RelativeLayout rlRoot;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvFrom = view.findViewById(R.id.tvFrom);
            rlRoot = view.findViewById(R.id.rlRoot);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<Jd> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public JobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_job_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final JobListAdapter.ViewHolder holder, final int position) {
        try{
            Jd bean = mDataSet.get(position);
            holder.tvTitle.setText(bean.getJobName());
            holder.tvDate.setText(bean.getPublishTime());
            holder.tvFrom.setText(bean.getFrom());

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
