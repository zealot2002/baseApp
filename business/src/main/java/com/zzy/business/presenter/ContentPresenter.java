package com.zzy.business.presenter;
import android.support.annotation.NonNull;

import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Content;
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

import java.io.IOException;
import java.util.List;

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
    public void create(final int type, final Content content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            if(content.getImgList().isEmpty()){
                HttpProxy.newContent(type,content,new CommonDataCallback() {
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
            new FileUploader().post(content.getImgList(), new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object data, Object tagData) {
                    MyLog.e("result:" +data.toString());
                    if (result == HConstant.SUCCESS) {
                        content.setImgList((List<Image>) data);
                        try {
                            HttpProxy.newContent(type,content,new CommonDataCallback() {
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
    public void like(final int position,int id) {
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
                        view.onLikeSuccess(position, (String) o);
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