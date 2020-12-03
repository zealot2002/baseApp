package com.zzy.home.presenter;

import android.support.annotation.NonNull;

import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.contract.MainContract;
import com.zzy.home.model.HttpProxy;
import com.zzy.home.model.bean.main.BannerBean;
import com.zzy.home.model.bean.main.NoticeBean;
import com.zzy.home.model.wrapper.HfCtx;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View view;
    private HfCtx hfCtx;
    private AtomicInteger dog;
    
    /****************************************************************************************************/
    public MainPresenter(@NonNull MainContract.View view) {
        this.view = view;
        hfCtx = new HfCtx();
        dog = new AtomicInteger(0);
    }
    
    @Override
    public void start() {
        if(!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showDisconnect();
            ToastUtils.showShort("请检查网络");
            return;
        }
        view.showLoading();
        dog.set(0);
        try {
            getBannerList();
            getNoticeList();
        } catch(Exception e) {
            e.printStackTrace();
            handleErrs();
        }
    }

    @Override
    public void end() {
        // TODO: need cancel req
    }


    private void getBannerList() throws Exception {
        HttpProxy.getBannerList(new CommonDataCallback() {
            @Override
            public void callback(int result, Object o, Object tagData) {
                if(result == HConstant.SUCCESS) {
                    hfCtx.setBannerList((List<BannerBean>) o);
                    updateUI();
                }
                else if(result == HConstant.FAIL
                        || result == HConstant.ERROR
                        ) {
                    handleErrs();
                }
            }
        });
    }
    
    private void updateUI() {
        /**
         getBannerList();
         getNoticeList();
         都回来之后，刷新ui
         **/
        if(dog.incrementAndGet() == 2) {
            view.updateUI(hfCtx);
            view.closeLoading();
        }
    }

    private void getNoticeList() throws Exception {
        HttpProxy.getNoticeList(new CommonDataCallback() {
            @Override
            public void callback(int result, Object o, Object o1) {
                if(result == HConstant.SUCCESS) {
                    hfCtx.setNoticeList((List<NoticeBean>) o);
                    updateUI();
                }
                else if(result == HConstant.FAIL
                        || result == HConstant.ERROR
                ) {
                    handleErrs();
                }
            }
        });
    }

    private void handleErrs() {
        view.closeLoading();
        view.showLoadingError();
    }
}