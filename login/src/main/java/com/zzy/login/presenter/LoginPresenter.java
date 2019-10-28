package com.zzy.login.presenter;
import android.support.annotation.NonNull;

import com.zzy.common.constants.SPConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.User;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileHandler;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;

import java.util.List;

/**
 * @author dell-7020
 * @Description:
 * @date 2018/08/07 16:25:23
 */

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
/****************************************************************************************************/
    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
    }

    private void handleErrs(String s){
        view.closeLoading();
        view.showError(s);
    }

    @Override
    public void login(String un, String pw) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.login(un,pw,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        //save token and userId
                        User user = (User) o;
                        SPUtils.putString(AppUtils.getApp(), SPConstants.TOKEN,user.getToken());
                        SPUtils.putString(AppUtils.getApp(), SPConstants.USER_ID,user.getId());
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
    public void register1(Archives bean) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.register1(bean,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
                        view.onFirstSuccess((String) o);
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
    public void register2(final Archives bean) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
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
    public void forgetPw(String phone, String pw, String sms) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.forgetPw(phone,pw,sms,new CommonDataCallback() {
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
    public void resetPw(String opw, String npw) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.resetPw(opw,npw,new CommonDataCallback() {
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
    public void getSms(String phone) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        view.showLoading();
        try{
            HttpProxy.getSms(phone,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    view.closeLoading();
                    if (result == HConstant.SUCCESS) {
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

    @Override
    public void getSkillTagList() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            view.showError(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
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

    @Override
    public void start() {

    }
}