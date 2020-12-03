package com.zzy.helper.action;

import android.content.Context;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.helper.model.HttpProxy;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.common.constants.ActionConstants;

import org.json.JSONObject;



@ScActionAnnotation(ActionConstants.GET_GLOBAL_CONFIG_ACTION)
public class GetGlobalConfigAction implements ScAction {

    @Override
    public void invoke(final Context context, final Bundle bundle, final String s, final ScCallback scCallback) {
        HttpProxy.getGlobalConfig(new HInterface.DataCallback() {
            @Override
            public void requestCallback(int result, Object data, Object tagData) {
                if (result == HConstant.SUCCESS) {
                    try {
                        JSONObject object = (JSONObject) data;
                        if(scCallback!=null)
                            scCallback.onCallback(true, null, "");
                        if(!object.toString().isEmpty()){
                            SPHelper.save(SPConstants.GLOBAL_CONFIG,object.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(scCallback!=null)
                            scCallback.onCallback(false, null, e.toString());
                    }
                }else if(result == HConstant.INTERCEPTED){

                } else {
                    if(scCallback!=null)
                        scCallback.onCallback(false, null, (String) data);
                }
            }
        });
    }
}
