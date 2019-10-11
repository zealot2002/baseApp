package com.zzy.business.contract;

import com.zzy.common.model.bean.Archives;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

import java.util.List;

public interface MyArchivesContract {

    interface View extends BaseLoadingView {
        void onSuccess();
        void showErr(String s);
        void onTagList(List<String> tagList);
    }

    interface Presenter extends BasePresenter {
        void updateArchives(Archives bean);
        void getSkillTagList();
    }

}
