package com.zzy.business.contract;

import com.zzy.common.model.bean.Goods;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface GoodsContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int type,int pageNum);
        void getDetail(int type,int id);
        void create(int type,Goods bean);
        void update(int type,Goods bean);
        void delete(int id);

        void createComment(int id, String content);
        void reply(int id, int commitId, String content);
        void report(int id, String content);
    }
}