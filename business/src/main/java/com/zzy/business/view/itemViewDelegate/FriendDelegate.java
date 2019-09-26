package com.zzy.business.view.itemViewDelegate;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.business.view.adapter.MessageAdapter;
import com.zzy.business.view.other.MessagePicturesLayout;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FriendDelegate implements ItemViewDelegate<FriendsCircle> {
    public interface OnEventListener {
        void onReport(int position);
        void onComment(int position);
        void onLike(int position);
    }
    private OnEventListener listener;
    private MessagePicturesLayout.Callback mCallback;
    private ImageView iAvatar;
    private TextView tNickname, tTime, tContent,tvCommentNum,tvLikeNum,tvReport;
    private MessagePicturesLayout lPictures;
    private LinearLayout lComment,lLike;


    public FriendDelegate(OnEventListener listener,MessagePicturesLayout.Callback mCallback) {
        this.listener = listener;
        this.mCallback = mCallback;
    }


    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_friends_list_item;
    }

    @Override
    public boolean isForViewType(FriendsCircle item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, FriendsCircle bean, final int position) {
        try{
            iAvatar =  holder.itemView.findViewById(R.id.i_avatar);
            tNickname =  holder.itemView.findViewById(R.id.t_nickname);
            tTime = holder.itemView.findViewById(R.id.t_time);
            tvCommentNum = holder.itemView.findViewById(R.id.tvCommentNum);
            tvLikeNum = holder.itemView.findViewById(R.id.tvLikeNum);
            tvReport = holder.itemView.findViewById(R.id.tvReport);
            tContent = holder.itemView.findViewById(R.id.t_content);
            lComment = holder.itemView.findViewById(R.id.lComment);
            lLike = holder.itemView.findViewById(R.id.lLike);

            lPictures = holder.itemView.findViewById(R.id.l_pictures);
            lPictures.setCallback(mCallback);


            Glide.with(holder.itemView.getContext()).load(bean.getAvatar()).into(iAvatar);
            tNickname.setText(bean.getAddress());
            tTime.setText(bean.getCreateTime());
            tvCommentNum.setText(bean.getLookNum());
            tvLikeNum.setText(bean.getLikeNum());
            tContent.setText(bean.getContent());
            lPictures.set(bean.getPictureThumbList(), bean.getPictureList());
            tvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onReport(position);
                    }
                }
            });
            lComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onComment(position);
                    }
                }
            });
            lLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onLike(position);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
