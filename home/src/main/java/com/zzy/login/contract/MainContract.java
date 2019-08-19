package com.zzy.login.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;



public interface MainContract {
    interface View extends BaseLoadingView {
        void showError(String s);
    }

    interface Presenter extends BasePresenter {

    }
}