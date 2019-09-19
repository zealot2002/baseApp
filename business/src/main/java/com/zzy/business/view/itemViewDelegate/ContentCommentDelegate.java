package com.zzy.business.view.itemViewDelegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.Comment;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class ContentCommentDelegate implements ItemViewDelegate<Comment> {
    public interface Listener{
        void onReply(int position);
    }
    private TextView tvUser,tvContent,tvReplyContent,tvReply,tvOwner;
    private Listener listener;
    private String ownerId;

    public ContentCommentDelegate(String ownerId,Listener listener) {
        this.listener = listener;
        this.ownerId = ownerId;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_content_comment_list_item;
    }

    @Override
    public boolean isForViewType(Comment item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Comment bean, final int position) {
        try{
            tvUser = holder.itemView.findViewById(R.id.tvUser);
            tvContent = holder.itemView.findViewById(R.id.tvContent);
            tvReplyContent = holder.itemView.findViewById(R.id.tvReplyContent);
            tvReply = holder.itemView.findViewById(R.id.tvReply);
            tvOwner = holder.itemView.findViewById(R.id.tvOwner);

            tvUser.setText(bean.getUserName());
            tvContent.setText(bean.getContent());

            if(CommonUtils.getUserId().equals(ownerId)){
                //帖子的作者
                tvReplyContent.setVisibility(View.GONE);
                tvOwner.setVisibility(View.GONE);
                tvReply.setVisibility(View.VISIBLE);
                tvReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onReply(position);
                    }
                });
            }else{
                //显示作者和作者回复的内容
                tvReply.setVisibility(View.GONE);
                tvReplyContent.setVisibility(View.VISIBLE);
                tvReplyContent.setText(bean.getReplyContent());
                if(!TextUtils.isEmpty(bean.getReplyContent())){
                    tvOwner.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
