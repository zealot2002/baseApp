package com.zzy.business.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface PbContract {
    interface View extends BaseLoadingView {
        void showError(String s);
    }

    interface Presenter extends BasePresenter {
        void getDataList(int pageNum);
    }
}