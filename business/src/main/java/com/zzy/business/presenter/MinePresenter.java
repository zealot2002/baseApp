package com.zzy.business.presenter;
import android.support.annotation.NonNull;

import com.zzy.business.R;
import com.zzy.business.contract.MineContract;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Image;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileUploader;
import com.zzy.commonlib.core.ThreadPool;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;

import org.json.JSONException;

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
        view.showError(s);
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

    @Override
    public void uploadUserHead(final Image image) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            ThreadPool.getInstance().getPool().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        new FileUploader().postHeadPic(image.getPath(), new HInterface.DataCallback() {
                            @Override
                            public void requestCallback(int result, Object data, Object tagData) {
                                MyLog.e("result:" +data.toString());
                                if (result == HConstant.SUCCESS) {
                                    view.onSuccess();
                                }else if(result == HConstant.FAIL){
                                    handleErrs((String) data);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            handleErrs(e.toString());
        }
    }
}