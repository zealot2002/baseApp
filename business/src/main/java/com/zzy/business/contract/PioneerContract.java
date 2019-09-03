package com.zzy.business.contract;

import com.zzy.business.model.bean.Job;
import com.zzy.business.model.bean.Menu;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;

import java.util.List;


public interface PioneerContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
        void updateMenuList(List<Menu> menuList);
    }

    interface Presenter extends BasePresenter {
        void getTypeList();
        void getList(String type, int pageNum);
        void getDetail(int id);
        void like(int id);
    }
}