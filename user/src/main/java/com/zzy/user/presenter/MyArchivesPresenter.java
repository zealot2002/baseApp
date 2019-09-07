package com.zzy.user.presenter;
import android.support.annotation.NonNull;

import com.zzy.common.model.HttpProxy;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.user.contract.MineContract;
import com.zzy.user.contract.MyArchivesContract;

public class MyArchivesPresenter implements MyArchivesContract.Presenter{
    private final MyArchivesContract.View view;
/****************************************************************************************************/
    public MyArchivesPresenter(@NonNull MyArchivesContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showDisconnect();
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getMyArchives(new CommonDataCallback() {
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
      //  ToastUtils.showShort(s);
    }

}