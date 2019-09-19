package com.zzy.common.network;

import android.app.Activity;
import android.os.Bundle;

import com.zzy.common.constants.ParamConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * @author zzy
 * @date 2019/1/22
 * 200	正常

 */

public class CommonInterceptor implements HInterface.Interceptor {

    @Override
    public boolean intercept(long receiveTime, String retString, Object tagObj){
        try{
            JSONTokener jsonParser = new JSONTokener(retString);
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            int errorCode = obj.getInt("code");
            if (errorCode == 105 ||errorCode == 106) {
                SCM.getInstance().req(AppUtils.getApp(),ActionConstants.ENTRY_LOGIN_ACTIVITY_ACTION);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
