package com.zzy.login.presenter;
import android.support.annotation.NonNull;

import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
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

public class MainPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
    private HfCtx hfCtx;
    private AtomicInteger dog;
/****************************************************************************************************/
    public MainPresenter(@NonNull LoginContract.View view) {
        this.view = view;
        hfCtx = new HfCtx();
        dog = new AtomicInteger(0);
    }
    @Override
    public void start() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showDisconnect();
            return;
        }
        view.showLoading();
        dog.set(0);
        try{
            getBannerList();
        }catch(Exception e){
            e.printStackTrace();
            handleErrs(e.toString());
        }
    }


    private void getBannerList() throws Exception{
        HttpProxy.getBannerList(new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object o, Object o1) {
                if (result == HConstant.SUCCESS) {
                    hfCtx.setBannerList((List<BannerBean>) o);
                    updateUI();
                }else if(result == HConstant.INTERCEPTED) {
                    //do nothing
                }else{
                    handleErrs((String) o);
                }
            }
        });
    }

    private void updateUI(){
        /**
        getBannerList();
        getNoticeList();
        getProjectInfo();
        getUpperShortcutList();
        getUnderShortcutList();
        都回来之后，刷新ui
        **/
        if(dog.incrementAndGet() == 1) {
            view.closeLoading();
            view.updateUI(hfCtx);
        }
    }
    private void handleErrs(String s){
        view.closeLoading();
        view.showDisconnect();
      //  ToastUtils.showShort(s);
    }
}