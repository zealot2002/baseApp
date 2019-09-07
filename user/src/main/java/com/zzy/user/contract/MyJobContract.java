package com.zzy.user.contract;

import com.zzy.common.model.bean.Job;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MyJobContract {

    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void getDetail(int id);
        void stop(int id);
        void update(Job bean);

    }

}
