package com.zzy.home.presenter;
import android.support.annotation.NonNull;

import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.HttpProxy;
import com.zzy.home.model.wrapper.HomeCtx;


public class HomePresenter implements HomeContract.Presenter{
    private final HomeContract.View view;
    private HomeCtx homeCtx;
/****************************************************************************************************/
    public HomePresenter(@NonNull HomeContract.View view) {
        this.view = view;
        homeCtx = new HomeCtx();
    }
    @Override
    public void start() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showDisconnect();
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getHomeData(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        view.updateUI(o);
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

    private void handleErrs(String s){
        view.closeLoading();
        view.showDisconnect();
    }
}