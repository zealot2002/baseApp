package com.zzy.business.contract;

import com.zzy.business.model.bean.Job;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface JobContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void getJobList(int pageNum);
        void getJobDetail(int id);
        void newJob(Job job);
        void report(int id, String content);
    }
}