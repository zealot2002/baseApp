package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;

import com.zzy.common.R;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseLoadingView;

/**
 * Created by haoran on 2018/11/20.
 */
public abstract class BaseToolbarLoadingFragment extends BaseToolbarFragment implements BaseLoadingView {

    private LoadingHelper loadingDialog;
    private View disconnectView, contentView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingDialog = new LoadingHelper(getActivity());
    }

    public void showLoading(String s) {
        if (loadingDialog != null) {
            loadingDialog.showLoading(s);
        }
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    public void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.closeLoading();
        }
    }

    @Override
    public void showDisconnect() {
        if (contentView != null) {
            getContainer().removeView(contentView);
        }
        if (disconnectView == null) {
            disconnectView = View.inflate(getActivity(), R.layout.disconnect, null);
            disconnectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload(false);
                }
            });
        }
        getContainer().addView(disconnectView);
    }

    @Override
    public void showLoadingError() {
        showDisconnect();
    }

    @Override
    public void reload(boolean b) {
    }

    @CallSuper
    @Override
    public void updateUI(Object o) {
        if (disconnectView != null) {
            getContainer().removeView(disconnectView);
        }
        if (contentView == null) {
            contentView = View.inflate(getActivity(), getLayoutId(), null);
            getContainer().addView(contentView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeLoading();
        loadingDialog = null;
    }

    protected abstract int getLayoutId();

}
