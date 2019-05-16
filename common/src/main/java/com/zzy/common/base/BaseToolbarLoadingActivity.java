package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zzy.common.R;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BackToolBar;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseLoadingView;

/**
 * Created by haoran on 2018/11/20.
 */
public abstract class BaseToolbarLoadingActivity extends BaseAppActivity implements BaseLoadingView {
    private LoadingHelper loadingDialog;
    private View disconnectView, contentView;
    private BackToolBar titleBar;

    /****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_titlebar_container);
        titleBar = findViewById(R.id.titleBar);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.llRoot));

        loadingDialog = new LoadingHelper(this);
    }

    public void showLoading(String s) {
        if (loadingDialog != null) {
            loadingDialog.showLoading(s);
        }
    }

    @Override
    public void showLoading() {
        showLoading("加载中...");
    }

    public void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.closeLoading();
        }
    }

    protected void setTitle(String title) {
        if (titleBar != null)
            titleBar.setTitle(title);
    }

    protected void setRightIv(@DrawableRes int res, View.OnClickListener listener) {
        if (titleBar != null)
            titleBar.setRightIv(res, listener);
    }

    protected void setOnBackEventListener(View.OnClickListener listener) {
        if (titleBar != null)
            titleBar.setOnBackEventListener(listener);
    }

    protected ViewGroup getContainer() {
        return findViewById(R.id.container);
    }

    @Override
    public void showDisconnect() {
        if (contentView != null) {
            getContainer().removeView(contentView);
        }
        if (disconnectView == null) {
            disconnectView = View.inflate(this, R.layout.disconnect, null);
            Button btnReload = disconnectView.findViewById(R.id.btnReload);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload(true);
                }
            });
        }
        if (getContainer().getChildCount() == 0) {
            getContainer().addView(disconnectView);
        }
    }

    @Override
    public void showLoadingError() {
        showDisconnect();
    }

    //子类复写此方法
    @Override
    public void reload(boolean b) {
    }

    //子类复写此方法

    @CallSuper
    @Override
    public void updateUI(Object o) {
        if (disconnectView != null) {
            getContainer().removeView(disconnectView);
        }
        if (contentView == null) {
            contentView = View.inflate(this, getLayoutId(), null);
        }
        if (getContainer().getChildCount() == 0) {
            getContainer().addView(contentView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoading();
        loadingDialog = null;
    }

    protected abstract int getLayoutId();
}