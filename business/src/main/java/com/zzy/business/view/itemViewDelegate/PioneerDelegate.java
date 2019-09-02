package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Pioneer;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class PioneerDelegate implements ItemViewDelegate<Pioneer> {
    private TextView tvTitle, tvDate,tvFrom;
    private Context context;

    public PioneerDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_pioneer_list_item;
    }

    @Override
    public boolean isForViewType(Pioneer item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Pioneer bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvDate = holder.itemView.findViewById(R.id.tvDate);
            tvFrom = holder.itemView.findViewById(R.id.tvFrom);

            tvTitle.setText(bean.getTitle());
            tvDate.setText(bean.getDate());
            tvFrom.setText(bean.getLookNum());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
