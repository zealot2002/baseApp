package com.zzy.business.contract;

import com.zzy.business.model.bean.Job;
import com.zzy.business.model.bean.Menu;
import com.zzy.business.model.bean.Pioneering;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

import java.util.List;


public interface PioneeringContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void getDetail(int id);
        void report(int id, String content);
        void newPioneering(Pioneering bean);
    }
}