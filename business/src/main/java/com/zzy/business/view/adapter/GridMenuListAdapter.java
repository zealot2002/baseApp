package com.zzy.business.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Job;
import com.zzy.business.model.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public class GridMenuListAdapter extends
        RecyclerView.Adapter<GridMenuListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<Menu> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

    /******************************************************************************************************************/
    public GridMenuListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn;
        public ViewHolder(View view) {
            super(view);
            btn = view.findViewById(R.id.btn);
        }
    }

    public void setOnItemClickedListener(Listener listener ){
        this.listener = listener;
    }
    public void swapData(List<Menu> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }
    @Override
    public GridMenuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_grid_menu_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final GridMenuListAdapter.ViewHolder holder, final int position) {
        try{
            Menu menu = mDataSet.get(position);
            holder.btn.setText(menu.getName());
            holder.btn.setSelected(menu.isSelected());
            holder.btn.setOnClickListener(new View.OnClickListener() {
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
