package com.zzy.home.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zzy.common.bean.message.NoticeBean;
import com.zzy.common.constants.ActionConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.contract.MainContract;
import com.zzy.home.model.HttpProxy;
import com.zzy.home.model.bean.main.BannerBean;
import com.zzy.home.model.wrapper.HfCtx;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;

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
        try {
            SCM.getInstance().cancel(ActionConstants.GET_MESSAGE_LIST_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            SCM.getInstance().req(AppUtils.getApp(), ActionConstants.GET_MESSAGE_LIST_ACTION, new ScCallback() {
                @Override
                public void onCallback(boolean b, Bundle data, String tag) {
                    if(b) {
                        List<NoticeBean> list = (List<NoticeBean>)data.getSerializable(ParamConstants.OBJECT);
                        hfCtx.setNoticeList(list);
                        updateUI();
                    }
                    else {
                        handleErrs();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleErrs() {
        view.closeLoading();
        view.showLoadingError();
    }
}