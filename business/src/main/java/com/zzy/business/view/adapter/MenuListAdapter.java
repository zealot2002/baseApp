package com.zzy.business.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuListAdapter extends
        RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    public interface Listener{
        void onItemClicked(int position);
    }
    private List<Menu> mDataSet = new ArrayList<>();
    private Listener listener;
    private Context context;

/******************************************************************************************************************/
    public MenuListAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RelativeLayout rlRoot;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            rlRoot = view.findViewById(R.id.rlRoot);
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
    public MenuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busi_menu_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MenuListAdapter.ViewHolder holder, final int position) {
        try{
            Menu menu = mDataSet.get(position);
            holder.tvName.setText(menu.getName());
            if(menu.isSelected()){
                holder.rlRoot.setBackgroundResource(R.color.white);
                holder.tvName.setTextColor(context.getResources().getColor(R.color.blue));
            }else{
                holder.rlRoot.setBackgroundResource(R.color.blue);
                holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
            }
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
