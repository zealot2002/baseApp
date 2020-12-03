package com.zzy.home.contract;

import com.zzy.common.base.BasePresenter;
import com.zzy.commonlib.base.BaseLoadingView;



public interface MainContract {
    interface View extends BaseLoadingView {
        void showError(String s);
    }

    interface Presenter extends BasePresenter {

    }
}