package com.zzy.home.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.scm.ActionConstants;


/**
 * @author zzy
 * @date 2018/8/13
 */
@ScActionAnnotation(ActionConstants.ENTRY_HOME_ACTIVITY_ACTION)
public class HomeEntryAction implements ScAction {

    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
//        intent.setClass(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        if (scCallback != null) {
            scCallback.onCallback(true, new Bundle(), "");
        }
    }
}
