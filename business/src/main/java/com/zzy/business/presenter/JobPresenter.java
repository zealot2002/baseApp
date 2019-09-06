package com.zzy.business.presenter;
import android.support.annotation.NonNull;

import com.zzy.business.R;
import com.zzy.business.contract.JobContract;
import com.zzy.business.model.HttpProxy;
import com.zzy.common.model.bean.Job;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/07 16:25:23
 */

public class JobPresenter implements JobContract.Presenter{
    private final JobContract.View view;
/****************************************************************************************************/
    public JobPresenter(@NonNull JobContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {
    }

    private void handleErrs(String s){
        view.closeLoading();
        view.showError(s);
//        view.showDisconnect();
      //  ToastUtils.showShort(s);
    }

    @Override
    public void getList(int pageNum) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getJobList(pageNum,new CommonDataCallback() {
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
    public void getDetail(int id) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getJobDetail(id,new CommonDataCallback() {
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
    public void create(Job job) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.newJob(job,new CommonDataCallback() {
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
    public void report(int id, String content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.reportJob(id,content,new CommonDataCallback() {
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
}