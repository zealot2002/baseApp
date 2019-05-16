package com.zzy.home.contract;

import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MineContract {

    interface View extends BaseLoadingView {


    }

    interface Presenter extends BasePresenter {

        void getUserInfo();


    }

}
