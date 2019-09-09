package com.zzy.login.contract;

import com.zzy.common.model.bean.Archives;
import com.zzy.commonlib.base.BaseLoadingView;
import com.zzy.commonlib.base.BasePresenter;


public interface LoginContract {
    interface View extends BaseLoadingView {
        void showError(String s);
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void login(String un,String pw);
        void register1(Archives bean);
        void register2(Archives bean);
        void forgetPw(String phone, String pw, String sms);
        void resetPw(String opw, String npw);
    }
}