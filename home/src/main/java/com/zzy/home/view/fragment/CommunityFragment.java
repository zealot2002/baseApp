package com.zzy.home.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.common.base.BaseLoadingFragment;
import com.zzy.home.R;

/**
 * @author zzy
 * @date 2018/9/14
 */

public class CommunityFragment extends BaseLoadingFragment {
    private Context context;

/****************************************************************************************************/
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        updateUI(null);
//        presenter = new PagePresenter(this);
//        presenter.getPageData(context, pageCode,true,1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_community_fragment;
    }
}
