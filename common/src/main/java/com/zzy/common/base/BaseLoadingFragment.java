package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zzy.common.R;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.base.BaseFragment;
import com.zzy.commonlib.base.BaseLoadingView;

/**
 * Created by haoran on 2018/11/20.
 */
public abstract class BaseLoadingFragment extends BaseFragment implements BaseLoadingView {

    private LoadingHelper loadingDialog;
    private View disconnectView, contentView;
    private View rootView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingDialog = new LoadingHelper(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.base_container, container, false);
        return rootView;
    }

    public void showLoading(String s) {
        if (loadingDialog != null) {
            loadingDialog.showLoading(s);
        }
    }

    @Override
    public void showLoading() {
        showLoading("加载中");
    }

    public void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.closeLoading();
        }
    }

    //删除所有view，disconnectView重新创建
    @Override
    public void showDisconnect() {
        try{
            getContainer().removeAllViews();
            disconnectView = View.inflate(getActivity(), R.layout.disconnect, null);
            Button btnReload = disconnectView.findViewById(R.id.btnReload);
            btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reload(true);
                    }
                });
            getContainer().addView(disconnectView);
        }catch(Exception e){
            e.printStackTrace();
        }
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
        if (contentView == null) {
            //第一次会走到这里
            contentView = View.inflate(getActivity(), getLayoutId(), null);
            getContainer().addView(contentView);
            return;
        }
        if (disconnectView != null) {
            //代码走到这里，说明上个页面一定是断网页面，断网页面删除contentView
            getContainer().removeView(disconnectView);
            getContainer().addView(contentView);
            disconnectView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeLoading();
        loadingDialog = null;
    }

    protected abstract int getLayoutId();

    protected ViewGroup getContainer() {
        return rootView.findViewById(R.id.container);
    }

}
