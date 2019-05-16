package com.zzy.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.web.constants.H5ActionConstants;
import com.zzy.web.utils.InnerUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MyWebActivity extends BaseAppActivity implements View.OnClickListener {
    private static final String TAG = "MyWebActivity";
    private WebView webView;
    private String fixedTitle;
    private LinearLayout llLeft;
    private TextView tvClose, tvTitle;
    private ImageView ivRight;
    private AndroidInterface androidInterface;

    /*********************************************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                |WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            setContentView(R.layout.web_myweb_activity);
            StatusBarUtils.setStatusBarFontIconLight(this, false);
            StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.llRoot));
            setupTitle();
            setupWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTitle() {
        llLeft = findViewById(R.id.llLeft);
        tvClose = findViewById(R.id.tvClose);
        tvTitle = findViewById(R.id.tvTitle);
        ivRight = findViewById(R.id.ivRight);
        fixedTitle = getIntent().getExtras().getString(ParamConstants.FIXED_TITLE);
        if (fixedTitle != null) {
            tvTitle.setText(fixedTitle);
        }
        llLeft.setOnClickListener(this);
        tvClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llLeft) {
            onBackPressed();
        } else if (v.getId() == R.id.tvClose) {
            finish();
        }
    }

    private void setupWebView() {
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(mWebViewClient);
        webView.setWebChromeClient(mWebChromeClient);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
//自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        androidInterface = new AndroidInterface(this);
        androidInterface.setAndroidInterfaceListener(
            new AndroidInterface.AndroidInterfaceListener() {
            @Override
            public void onSendMsg(final String msg) {
                ivRight.post(new Runnable() {
                    @Override
                    public void run() {
                        handleMsg(msg);
                    }
                });
            }
        });
        webView.addJavascriptInterface(androidInterface, "jsObj");
        renderWebPage();
    }

    private void handleMsg(final String msg) {
        Log.e(TAG, "H5 handleMsg msg:" + msg);
        try{
            JSONTokener jsonParser = new JSONTokener(msg);
            final JSONObject obj = (JSONObject) jsonParser.nextValue();
            String action = obj.getString("action");
            if (action.equals(H5ActionConstants.SET_TITLEBAR_RIGHT_ICON)) {
                String iconType = obj.getString("iconType");
                if(iconType.equals("0")){
                    ivRight.setVisibility(View.GONE);
                }else if(iconType.equals("1")){
                    //分享
                    ivRight.setVisibility(View.VISIBLE);
                    ivRight.setImageResource(R.mipmap.three_dot);
                    ivRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                InnerUtils.showShare(MyWebActivity.this,
                                        obj.getJSONObject("jsonData").getString("title"),
                                        obj.getJSONObject("jsonData").getString("content"),
                                        obj.getJSONObject("jsonData").getString("imgUrl"),
                                        obj.getJSONObject("jsonData").getString("targetUrl"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if(iconType.equals("2")){
                    //扶贫？按钮
                    ivRight.setVisibility(View.VISIBLE);
                    ivRight.setImageResource(R.mipmap.red_question_mask);
                    ivRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String param = "{\"action\":\"showHelpPoorPopupWin\"}";
                                callJs(param);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void renderWebPage() {
        String renderType = getIntent().getExtras().getString(ParamConstants.TYPE, "loadUrl");
        String url = getIntent().getExtras().getString(ParamConstants.URL, "");
        String data = getIntent().getExtras().getString(ParamConstants.DATA, "");

        if (renderType.equals("loadData")) {
            if (data.isEmpty()) {
                ToastUtils.showShort("webView: error! Type is loadData but data is empty!");
                return;
            }
            webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
        } else if (renderType.equals("postUrl")) {
            if (data.isEmpty() || url.isEmpty()) {
                ToastUtils.showShort("webView: error! Type is postUrl but data or url is empty!");
                return;
            }
            webView.postUrl(url, data.getBytes());
        } else {
            //load url
            if (url.isEmpty()) {
                ToastUtils.showShort("webView: error! url is empty!");
                return;
            }
            webView.loadUrl(url);
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {}

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            String param = "{\"action\":\"resize\"}";
//            callJs(param);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (fixedTitle == null) {
                /*fixedTitle，则title跟随变化*/
                if (!TextUtils.isEmpty(title))
                    tvTitle.setText(title.length()>8?String.format("%s...", title.substring(0, 8)):title);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        androidInterface = null;
        AppUtils.exitApp(this);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    //分享回调结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        int CHANNEL_WECHAT = 0;
//        int CHANNEL_WECHAT_MOMENT = 1;
//        int CHANNEL_QQ = 2;
//        int CHANNEL_QQ_ZONE = 3;
//        int CHANNEL_WEIBO = 4;
//        int CHANNEL_MORE = 5;
//        int CHANNEL_CANNEL = -1;
        if(requestCode == 0
            ||requestCode == 1
            ||requestCode == 2
            ||requestCode == 3
            ||requestCode == 4
            ){
            String param = "{\"action\":\"notifyShareResult\",\"result\":\"success\"}";
            callJs(param);
        }
    }

    void callJs(String s){
        String js = "javascript:sendMsg('" + s + "')";
        webView.evaluateJavascript(js, null);
    }
}