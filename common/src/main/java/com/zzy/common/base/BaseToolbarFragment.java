package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.common.R;
import com.zzy.common.widget.BackToolBar;
import com.zzy.commonlib.base.BaseFragment;

/**
 * Created by haoran on 2018/11/20.
 */
public abstract class BaseToolbarFragment extends BaseFragment {

    private BackToolBar titleBar;
    private View rootView, contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.base_titlebar_container, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        titleBar = rootView.findViewById(R.id.titleBar);
        if (contentView == null) {
            contentView = View.inflate(getActivity(), getLayoutId(), null);
            getContainer().addView(contentView);
        }
    }

    protected void setTitle(String title) {
        if (titleBar != null)
            titleBar.setTitle(title);
    }

    protected void setOnBackEventListener(View.OnClickListener listener) {
        if (titleBar != null)
            titleBar.setOnBackEventListener(listener);
    }

    protected ViewGroup getContainer() {
        return rootView.findViewById(R.id.container);
    }

    protected abstract int getLayoutId();

}
