package com.zzy.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaiky.imagespickers.ImageLoader;
import com.zzy.common.R;

public class GlideLoader implements ImageLoader {

	private static final long serialVersionUID = 1L;

	@Override
    public void displayImage(Context context, String path, ImageView imageView) {
        com.zzy.common.glide.ImageLoader.loadImage(imageView,path);
    }

}
