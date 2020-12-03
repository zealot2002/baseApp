package com.zzy.web.utils;

import android.content.Context;
import android.os.Bundle;

import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.common.constants.ActionConstants;
import com.zzy.web.R;

import java.net.URLDecoder;

public class InnerUtils {
    /**
     *
     * @param title 分享标题
     * @param content 分享内容
     * @param imgUrl 分享图片链接
     * @param targetUrl 分享内容链接
     */
    public static void showShare(Context context,
                 String title, String content, String imgUrl, String targetUrl) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("title", URLDecoder.decode(title, "utf-8"));
            bundle.putString("content", URLDecoder.decode(content, "utf-8"));
            bundle.putString("url", URLDecoder.decode(imgUrl, "utf-8"));
            bundle.putString("actionUrl", URLDecoder.decode(targetUrl, "utf-8"));
            bundle.putInt("defaultIcon", R.mipmap.icon);
            SCM.getInstance().req(context, ActionConstants.SHOW_SHARE_DIALOG_ACTION, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
