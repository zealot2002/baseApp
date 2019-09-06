package com.zzy.business.view.itemViewDelegate;

import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.Content;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class ContentDelegate implements ItemViewDelegate<Content> {
    private TextView tvTitle,tvLeft,tvMiddle,tvRight;
    private int type;

    public ContentDelegate(int type) {
        this.type = type;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_content_list_item;
    }

    @Override
    public boolean isForViewType(Content item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Content bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvLeft = holder.itemView.findViewById(R.id.tvLeft);
            tvMiddle = holder.itemView.findViewById(R.id.tvMiddle);
            tvRight = holder.itemView.findViewById(R.id.tvRight);

            tvTitle.setText(bean.getTitle());
            tvLeft.setText(bean.getDate());

            if(CommonConstants.CONTENT_HELP == type){
                tvMiddle.setText(bean.getFrom());
                tvRight.setText("浏览:"+bean.getLookNum());
            }else if(CommonConstants.CONTENT_IDEA == type){
                tvMiddle.setText("浏览:"+bean.getLookNum());
                tvRight.setText("回复:"+bean.getCommentList().size());
            }else if(CommonConstants.CONTENT_EXPERIENCE == type){
                tvMiddle.setText("浏览:"+bean.getLookNum());
                tvRight.setText("回复:"+bean.getCommentList().size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
