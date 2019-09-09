package com.zzy.user.contract;

import com.zzy.common.model.bean.Log;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MyLogContract {

    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void create(Log bean);
    }

}
