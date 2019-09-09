package com.zzy.user.view.itemViewDelegate;

import android.content.Context;
import android.widget.TextView;

import com.zzy.common.model.bean.Log;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.user.R;

public class LogDelegate implements ItemViewDelegate<Log> {
    private TextView tvContent, tvDate,tvTime;
    private Context context;

    public LogDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.user_my_log_list_item;
    }

    @Override
    public boolean isForViewType(Log item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Log bean, final int position) {
        try{
            tvContent = holder.itemView.findViewById(R.id.tvContent);
            tvDate = holder.itemView.findViewById(R.id.tvDate);
            tvTime = holder.itemView.findViewById(R.id.tvTime);

            tvContent.setText(bean.getContent());
            tvDate.setText(bean.getDate());
            tvTime.setText(bean.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
