package com.zzy.business.presenter;
import android.support.annotation.NonNull;

import com.zzy.business.contract.MineContract;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;

public class MinePresenter implements MineContract.Presenter{
    private final MineContract.View view;
/****************************************************************************************************/
    public MinePresenter(@NonNull MineContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
    }

    private void handleErrs(String s){
        view.closeLoading();
        view.showDisconnect();
      //  ToastUtils.showShort(s);
    }

    @Override
    public void getUserInfo() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showDisconnect();
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getUserInfo(new CommonDataCallback() {
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
}