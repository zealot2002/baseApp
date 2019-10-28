package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BackToolBar;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.utils.ToastUtils;

/**
 */
public abstract class BaseTitleAndBottomBarActivity extends BaseAppActivity implements BaseLoadingView {
    private TextView tvTitle;
    private RelativeLayout rlBack,rlDisconnect;
    private LoadingHelper loadingHelper;
    private Button btnReload;
    /****************************************************************************************************/
    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_title_and_bottom_bar_container);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rlRoot));
        loadingHelper = new LoadingHelper(this);
        View view = View.inflate(this, getLayoutId(), null);
        getContainer().addView(view);
        rlDisconnect = findViewById(R.id.rlDisconnect);
        btnReload = findViewById(R.id.btnReload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload(true);
            }
        });
        tvTitle = findViewById(R.id.tvTitle);
        rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setOnBackClickListener(View.OnClickListener listener){
        if(rlBack!=null){
            rlBack.setOnClickListener(listener);
        }
    }

    protected void setTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }

    protected ViewGroup getContainer() {
        return findViewById(R.id.container);
    }

    protected abstract int getLayoutId();


    @Override
    public void showLoading() {
        loadingHelper.showLoading();
    }

    @Override
    public void closeLoading() {
        loadingHelper.closeLoading();
    }

    @CallSuper
    @Override
    public void showDisconnect() {
        rlDisconnect.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingError() {

    }

    @CallSuper
    @Override
    public void updateUI(Object o){
        rlDisconnect.setVisibility(View.GONE);

    }

}
