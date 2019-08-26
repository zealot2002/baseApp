package com.zzy.home.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;



public interface HomeContract {
    interface View extends BaseLoadingView {
        void showError(String s);
    }

    interface Presenter extends BasePresenter {

    }
}