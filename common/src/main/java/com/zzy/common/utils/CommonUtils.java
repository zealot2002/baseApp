package com.zzy.common.utils;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.commonlib.utils.encryptUtils.MD5Utils;

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
}
