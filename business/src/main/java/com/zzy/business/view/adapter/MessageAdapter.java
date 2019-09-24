package com.zzy.business.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzy.business.R;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.business.view.other.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    public interface OnEventListener {
        void onReport(int position);
        void onComment(int position);
        void onLike(int position);
    }
    private final List<FriendsCircle> mDataList = new ArrayList<>();
    private MessagePicturesLayout.Callback mCallback;

    private OnEventListener listener;
    public MessageAdapter(Context context) {
    }

    public void setListener(OnEventListener listener) {
        this.listener = listener;
    }

    public MessageAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }

    public void set(List<FriendsCircle> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iAvatar;
        TextView tNickname, tTime, tContent,tvCommentNum,tvLikeNum,tvReport;
        MessagePicturesLayout lPictures;
        LinearLayout lComment,lLike;

        FriendsCircle mData;

        ViewHolder(View itemView) {
            super(itemView);
            iAvatar =  itemView.findViewById(R.id.i_avatar);
            tNickname =  itemView.findViewById(R.id.t_nickname);
            tTime = itemView.findViewById(R.id.t_time);
            tvCommentNum = itemView.findViewById(R.id.tvCommentNum);
            tvLikeNum = itemView.findViewById(R.id.tvLikeNum);
            tvReport = itemView.findViewById(R.id.tvReport);
            tContent = itemView.findViewById(R.id.t_content);
            lComment = itemView.findViewById(R.id.lComment);
            lLike = itemView.findViewById(R.id.lLike);

            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictures);
            lPictures.setCallback(mCallback);
        }

        void refresh(final int pos) {
            mData = mDataList.get(pos);
            Glide.with(itemView.getContext()).load(mData.getAvatar()).into(iAvatar);
            tNickname.setText(mData.getAddress());
            tTime.setText(mData.getCreateTime());
            tvCommentNum.setText(mData.getLookNum());
            tvLikeNum.setText(mData.getLikeNum());
            tContent.setText(mData.getContent());
            lPictures.set(mData.getPictureThumbList(), mData.getPictureList());
            tvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onReport(pos);
                    }
                }
            });
            lComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onComment(pos);
                    }
                }
            });
            lLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onLike(pos);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.busi_friends_list_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).refresh(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null?0:mDataList.size();
    }
}
