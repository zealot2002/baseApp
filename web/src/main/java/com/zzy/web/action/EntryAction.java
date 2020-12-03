package com.zzy.web.action;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.common.constants.ParamConstants;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.common.constants.ActionConstants;
import com.zzy.web.MyWebActivity;

/**
 * @author zzy
 * @date 2018/8/13
 */
/*
用法：
    Bundle bundle = new Bundle();
    //必选参数
    bundle.putString(ParamConstants.URL, "http://www.baidu.com");

    //可选参数
    bundle.putString(ParamConstants.TYPE,"loadUrl|loadData|postUrl");
    //可选参数
    bundle.putString(ParamConstants.FIXED_TITLE, "固定的title");
    //可选参数
    bundle.putString(ParamConstants.DATA,"data........");

    //call webView
    SCM.getInstance().req(AppUtils.getApp(), ActionConstants.ENTRY_WEB_VIEW_ACTIVITY_ACTION, bundle);

* */
@ScActionAnnotation(ActionConstants.ENTRY_WEB_VIEW_ACTIVITY_ACTION)
public class EntryAction implements ScAction {
    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {
        if(bundle == null){
            if(scCallback!=null){
                Bundle bundle1 = new Bundle();
                bundle1.putString(ParamConstants.ERROR,"error param");
                scCallback.onCallback(false, bundle1,"");
            }
            return;
        }

        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, MyWebActivity.class);

        if(context instanceof Application){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

        if(scCallback!=null){
            scCallback.onCallback(true, new Bundle(), "");
        }
    }

    @Override
    public void cancel() {

    }
}
