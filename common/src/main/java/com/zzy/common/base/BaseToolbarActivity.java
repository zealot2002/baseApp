package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.common.R;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BackToolBar;


/**
 */
public abstract class BaseToolbarActivity extends BaseAppActivity {
    private BackToolBar titleBar;

    /****************************************************************************************************/
    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_titlebar_container);
        titleBar = findViewById(R.id.titleBar);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.llRoot));
        View view = View.inflate(this, getLayoutId(), null);
        getContainer().addView(view);
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

    protected abstract int getLayoutId();


}
