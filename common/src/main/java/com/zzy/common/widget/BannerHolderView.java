package com.zzy.common.widget;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.zzy.common.R;
import com.zzy.common.glide.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class BannerHolderView extends Holder<String> {
    private ImageView ivPic;
    private int errResId;
    public BannerHolderView(View itemView, int errResId) {
        super(itemView);
        this.errResId = errResId;
    }

    @Override
    protected void initView(View itemView) {
        ivPic = itemView.findViewById(R.id.ivPic);
    }

    @Override
    public void updateUI(String url) {
        ImageLoader.loadImage(ivPic.getContext(),ivPic,url,errResId);
    }
}