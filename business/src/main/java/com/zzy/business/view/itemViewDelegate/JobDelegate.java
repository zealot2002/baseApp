package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.model.bean.Job;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class JobDelegate implements ItemViewDelegate<Job> {
    private TextView tvTitle, tvDate,tvFrom;
    private Context context;

    public JobDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_job_list_item;
    }

    @Override
    public boolean isForViewType(Job item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Job bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvDate = holder.itemView.findViewById(R.id.tvDate);
            tvFrom = holder.itemView.findViewById(R.id.tvFrom);

            tvTitle.setText(bean.getJobName());
            tvDate.setText(bean.getPublishTime());
            tvFrom.setText(bean.getFrom());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
