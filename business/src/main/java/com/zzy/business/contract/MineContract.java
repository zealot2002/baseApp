package com.zzy.business.contract;

import com.zzy.common.model.bean.Image;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MineContract {

    interface View extends BaseLoadingView {
        void onSuccess();
        void showError(String s);
    }

    interface Presenter extends BasePresenter {
        void getUserInfo();
        void uploadUserHead(Image image);
    }

}
