package com.zzy.business.contract;

import com.zzy.common.model.bean.Pioneering;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface PioneeringContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void getDetail(int type,int id);
        void report(int id, String content);
        void create(Pioneering bean);
    }
}