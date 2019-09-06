package com.zzy.business.contract;

import com.zzy.common.model.bean.Content;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface ContentContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getList(int type,int pageNum);
        void create(int type, Content bean);
        void getDetail(int id);
        void like(int id);
        void report(int id,String content);
        void createComment(int contentId,String content);
        void reply(int contentId,int commentId,String content);
    }
}