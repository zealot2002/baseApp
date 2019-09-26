package com.zzy.business.contract;

import com.zzy.common.model.bean.Archives;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

public interface MyArchivesContract {

    interface View extends BaseLoadingView {
        void onSuccess();
        void showErr(String s);
    }

    interface Presenter extends BasePresenter {
        void updateArchives(Archives bean);
    }

}
