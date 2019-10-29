package com.zzy.business.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.business.R;
import com.zzy.business.view.activity.MyMainActivity;
import com.zzy.business.view.activity.SettingsActivity;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.servercentre.ActionConstants;


/**
 * @author zzy
 * @date 2018/8/13
 */
@ScActionAnnotation(ActionConstants.LOGOUT_ACTION)
public class LogoutAction implements ScAction {

    @Override
    public void invoke(final Context context, Bundle bundle, String s, final ScCallback scCallback) {
        Intent intent = new Intent();
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort("网络异常，请稍后重试");
            if (scCallback != null) {
                scCallback.onCallback(false, new Bundle(), "");
            }
            return;
        }
        try{
            HttpProxy.logout(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    if (result == HConstant.SUCCESS) {
                        //clear token and userId
                        SPUtils.putString(AppUtils.getApp(), SPConstants.TOKEN,"");
                        SPUtils.putString(AppUtils.getApp(), SPConstants.USER_ID,"");
                        try {
                            SCM.getInstance().req(context,
                                    ActionConstants.ENTRY_LOGIN_ACTIVITY_ACTION);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        ToastUtils.showShort((String) o);
                        if (scCallback != null) {
                            scCallback.onCallback(false, new Bundle(), "");
                        }
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
            if (scCallback != null) {
                scCallback.onCallback(false, new Bundle(), "");
            }
        }
        if (scCallback != null) {
            scCallback.onCallback(true, new Bundle(), "");
        }
    }
}
