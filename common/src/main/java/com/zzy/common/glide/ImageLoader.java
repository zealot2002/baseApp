package com.zzy.common.glide;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zzy.common.R;

public class ImageLoader {


    private ImageLoader() {
        throw new IllegalStateException(" cannot to new the Object ");
    }


    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        loadImage(context, imageView, imageUrl);

    }


    public static void loadImageWithPlaceHolder(ImageView imageView, String imageUrl, @DrawableRes int placeholderRes, @DrawableRes int errorRes) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        loadImageWithPlaceHolder(context, imageView, imageUrl, placeholderRes, errorRes);
    }

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        try {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .format(DecodeFormat.PREFER_RGB_565)
                            .disallowHardwareConfig()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadImageAsGif(Context context, ImageView imageView, String imageUrl) {
        try {
            Glide.with(context)
                    .asGif()
                    .load(imageUrl)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(Context context, ImageView imageView, String imageUrl, @DrawableRes int placeholderRes) {
        try {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(
                            new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .placeholder(placeholderRes))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageWithPlaceHolder(Context context, ImageView imageView, String imageUrl, @DrawableRes int placeholderRes, @DrawableRes int errorRes) {
        try {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(
                            new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .placeholder(placeholderRes))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
