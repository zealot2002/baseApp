package com.zzy.login.presenter;
import android.support.annotation.NonNull;

import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.model.HttpProxy;
import com.zzy.login.model.bean.main.BannerBean;
import com.zzy.login.model.wrapper.HfCtx;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/07 16:25:23
 */

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
/****************************************************************************************************/
    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
    }

    private void handleErrs(String s){
        view.closeLoading();
        view.showDisconnect();
      //  ToastUtils.showShort(s);
    }

    @Override
    public void login(String un, String pw) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.login(un,pw,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        view.onSuccess();
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        handleErrs((String) o);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            handleErrs(e.toString());
        }
    }

    @Override
    public void start() {

    }
}