package com.zzy.business.view.activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;

/**
 * 特色东坑
 */
public class SpecialDkActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private RelativeLayout rlMenu1,rlMenu2,rlMenu3;
    private TextView tv1,tv2,tv3;
    private WebView webView;
    private static final String URL1 = "http://www.baidu.com";
    private static final String URL2 = "http://www.sohu.com";
    private static final String URL3 = "http://www.sina.com";
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("特色东坑");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_special_dk_activity;
    }

    private void setupViews() {
        rlMenu1 = findViewById(R.id.rlMenu1);
        rlMenu2 = findViewById(R.id.rlMenu2);
        rlMenu3 = findViewById(R.id.rlMenu3);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        rlMenu1.setOnClickListener(this);
        rlMenu2.setOnClickListener(this);
        rlMenu3.setOnClickListener(this);

        initWebView();
        webView.loadUrl(URL1);
    }
    private void reset(){
        rlMenu1.setBackgroundResource(R.color.translucent);
        rlMenu2.setBackgroundResource(R.color.translucent);
        rlMenu3.setBackgroundResource(R.color.translucent);
        tv1.setTextColor(getResources().getColor(R.color.white));
        tv2.setTextColor(getResources().getColor(R.color.white));
        tv3.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View v) {
        reset();
        if(v.getId() == R.id.rlMenu1){
            rlMenu1.setBackgroundResource(R.color.white);
            tv1.setTextColor(getResources().getColor(R.color.blue));
            webView.loadUrl(URL1);
        }else if(v.getId() == R.id.rlMenu2){
            rlMenu2.setBackgroundResource(R.color.white);
            tv2.setTextColor(getResources().getColor(R.color.blue));
            webView.loadUrl(URL2);
        }else if(v.getId() == R.id.rlMenu3){
            rlMenu3.setBackgroundResource(R.color.white);
            tv3.setTextColor(getResources().getColor(R.color.blue));
            webView.loadUrl(URL3);
        }

    }

    private void initWebView() {
        webView = findViewById(R.id.webView);
        com.tencent.smtt.sdk.WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。MIXED_CONTENT_ALWAYS_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(com.tencent.smtt.sdk.WebSettings.LOAD_NORMAL);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
        });
    }
}
