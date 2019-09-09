package com.zzy.user.contract;

import com.zzy.common.model.bean.Pioneering;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MyPioneeringContract {

    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void getDetail(int id);
        void stop(int id);
        void update(Pioneering bean);

    }

}
