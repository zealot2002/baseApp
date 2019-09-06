package com.zzy.business.presenter;
import android.support.annotation.NonNull;

import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.model.HttpProxy;
import com.zzy.common.model.bean.Content;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/07 16:25:23
 */

public class ContentPresenter implements ContentContract.Presenter{
    private final ContentContract.View view;
/****************************************************************************************************/
    public ContentPresenter(@NonNull ContentContract.View view) {
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
    public void getList(int type,int pageNum) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getContentList(type,pageNum,new CommonDataCallback() {
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
            HttpProxy.getContentDetail(id,new CommonDataCallback() {
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
    public void create(int type, final Content content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
//            ThreadPool.getInstance().getPool().submit(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        FileUploader.post(content.getImgList().get(0).getPath());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            HttpProxy.newContent(type,content,new CommonDataCallback() {
//                @Override
//                public void callback(int result, Object o, Object o1) {
//                    view.closeLoading();
//                    if (result == HConstant.SUCCESS) {
//                        view.onSuccess();
//                    }else if(result == HConstant.FAIL
//                            ||result == HConstant.ERROR
//                    ){
//                        handleErrs((String) o);
//                    }
//                }
//            });
        }catch(Exception e){
            e.printStackTrace();
            handleErrs(e.toString());
        }
    }

    @Override
    public void like(int id) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.likeContent(id,new CommonDataCallback() {
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
            HttpProxy.reportContent(id,content,new CommonDataCallback() {
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
    public void createComment(int contentId, String content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.createComment(contentId,content,new CommonDataCallback() {
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
    public void reply(int contentId, int commentId, String content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.createReply(contentId,commentId,content,new CommonDataCallback() {
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