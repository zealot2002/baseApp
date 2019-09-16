package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.model.bean.Expert;
import com.zzy.common.model.bean.HelpClass;
import com.zzy.common.model.bean.PbRecord;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class ExpertDelegate implements ItemViewDelegate<HelpClass.Parter> {
    private ImageView ivPic;
    private Context context;

    public ExpertDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_expert_list_item;
    }

    @Override
    public boolean isForViewType(HelpClass.Parter item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, HelpClass.Parter bean, final int position) {
        try{
            ivPic = holder.itemView.findViewById(R.id.ivPic);

            ImageLoader.loadImage(ivPic,bean.headUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
