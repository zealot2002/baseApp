package com.zzy.common.view.itemViewDelegate;

import android.content.Context;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.common.model.bean.Comment;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class CommentDelegate implements ItemViewDelegate<Comment> {
    private TextView tvTitle, tvDate;
    private Context context;

    public CommentDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.user_comment_list_item;
    }

    @Override
    public boolean isForViewType(Comment item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Comment bean, final int position) {
        try{
            tvTitle = holder.itemView.findViewById(R.id.tvTitle);
            tvDate = holder.itemView.findViewById(R.id.tvDate);

            tvTitle.setText(bean.getUserName());
            tvDate.setText(bean.getDate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
