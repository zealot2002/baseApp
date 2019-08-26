package com.zzy.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.home.model.bean.News;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends
        RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<News> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

/******************************************************************************************************************/
    public NewsListAdapter(Context context){
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
            ivPic = view.findViewById(R.id.ivPic);
            rlRoot = view.findViewById(R.id.rlRoot);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<News> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_get_rich_info_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final NewsListAdapter.ViewHolder holder, final int position) {
        try{
            News bean = mDataSet.get(position);
            holder.tvTitle.setText(bean.getTitle());
            holder.tvDate.setText(bean.getPublishTime());
            holder.tvFrom.setText(bean.getFrom());

            holder.ivPic.setVisibility(position == 0?View.VISIBLE:View.GONE);

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
