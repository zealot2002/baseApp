package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class PioneeringDelegate implements ItemViewDelegate<Pioneering> {
    private TextView tvTitle, tvDate;
    private Context context;

    public PioneeringDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_pioneering_list_item;
    }

    @Override
    public boolean isForViewType(Pioneering item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Pioneering bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvDate = holder.itemView.findViewById(R.id.tvDate);

            tvTitle.setText(bean.getTitle());
            tvDate.setText(bean.getDate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
