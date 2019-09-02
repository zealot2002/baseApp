package com.zzy.business.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface GetRichInfoContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onLikeSuccess();
    }

    interface Presenter extends BasePresenter {
        void getRichInfoList(int pageNum);
        void getRichInfoDetail(int id);
        void like(int id);
    }
}