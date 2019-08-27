package com.zzy.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzy.home.R;
import com.zzy.home.model.bean.SaleInfo;

import java.util.ArrayList;
import java.util.List;

public class SaleListAdapter extends
        RecyclerView.Adapter<SaleListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<SaleInfo> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

/******************************************************************************************************************/
    public SaleListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<SaleInfo> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public SaleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_sale_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final SaleListAdapter.ViewHolder holder, final int position) {
        try{
            int index = 0;
            if(position!=0){
//                index = position%mDataSet.size();
            }
//            News bean = mDataSet.get(index);
//            holder.tvTitle.setText(bean.getTitle());
//            holder.tvDate.setText(bean.getPublishTime());
//            holder.tvFrom.setText(bean.getFrom());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mDataSet == null?0:Integer.MAX_VALUE;
    }
}
