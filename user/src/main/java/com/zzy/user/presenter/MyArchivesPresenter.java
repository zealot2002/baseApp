package com.zzy.user.presenter;
import android.support.annotation.NonNull;

import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.jsonParser.ImageParser;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileUploader;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.user.R;
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
            view.showErr(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
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
        view.showErr(s);
    }

    @Override
    public void updateArchives(final Archives bean) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showErr(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            if(!bean.getIsCompany().equals("是")){
                HttpProxy.updateArchives(bean,new CommonDataCallback() {
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
                return;
            }
            FileUploader.post(bean.getCompanyImgUrl(), new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object o, Object tagData) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        try {
                            Image image = ImageParser.parse((String) o);
                            if(image!=null){
                                bean.setCompanyImgName(image.getName());
                                bean.setCompanyImgUrl(image.getPath());
                            }
                            HttpProxy.updateArchives(bean,new CommonDataCallback() {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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