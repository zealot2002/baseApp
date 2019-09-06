package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.GetRichInfo;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class GetRichInfoDelegate implements ItemViewDelegate<GetRichInfo> {
    private TextView tvTitle, tvDate,tvFrom;
    private ImageView ivPic;
    private Context context;

    public GetRichInfoDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_get_rich_info_list_item;
    }

    @Override
    public boolean isForViewType(GetRichInfo item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, GetRichInfo bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvDate = holder.itemView.findViewById(R.id.tvDate);
            tvFrom = holder.itemView.findViewById(R.id.tvFrom);
            ivPic = holder.itemView.findViewById(R.id.ivPic);

            tvTitle.setText(bean.getTitle());
            tvDate.setText(bean.getDate());
            tvFrom.setText(bean.getFrom());
            ivPic.setVisibility(bean.isPlaceTop()? View.VISIBLE:View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
