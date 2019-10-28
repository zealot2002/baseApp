package com.zzy.business.presenter;

import android.support.annotation.NonNull;

import com.zzy.business.R;
import com.zzy.business.contract.MyArchivesContract;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.Image;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileHandler;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;

import java.util.List;

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
            if(bean.getImgList().isEmpty()){
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
            new FileHandler().post(bean.getImgList(), new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object data, Object tagData) {
                    MyLog.e("result:" +data.toString());
                    if (result == HConstant.SUCCESS) {
                        bean.setImgList((List<Image>) data);
                        try {
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
                            handleErrs(e.toString());
                        }
                    }else if(result == HConstant.FAIL){
                        handleErrs((String) data);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            handleErrs(e.toString());
        }
    }

    @Override
    public void getSkillTagList() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showErr(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getSkillTagList(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        view.onTagList((List<String>) o);
//                        view.onSuccess();
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