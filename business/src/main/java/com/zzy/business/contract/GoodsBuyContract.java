package com.zzy.business.contract;

import com.zzy.business.model.bean.Goods;
import com.zzy.business.model.bean.Pioneering;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface GoodsBuyContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int pageNum);
        void getDetail(int id);
        void report(int id, String content);
        void create(Goods bean);
    }
}