package com.zzy.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zzy.common.R;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.commonlib.utils.encryptUtils.MD5Utils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

import java.net.URLDecoder;

public final class CommonUtils {
    public static String getUserId(){
        String userId = SPUtils.getString(AppUtils.getApp(), SPConstants.USER_ID,"");
        return userId;
    }
    public static String getToken(){
        String token = SPUtils.getString(AppUtils.getApp(), SPConstants.TOKEN, "");
        return token;
    }
    public static String getPw(String pw){
        return MD5Utils.encode(pw);
    }

    public static void webLoadData(final WebView webView ,String htmlData){
        webView.loadDataWithBaseURL(null,htmlData, "text/html",  "utf-8", null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                webView.measure(w, h);
            }
        });
    }

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
