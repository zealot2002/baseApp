package com.zzy.message.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzy.message.view.activity.MessageActivity;
import com.zzy.annotations.ScActionAnnotation;
import com.zzy.common.constants.ActionConstants;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;

/**
 * @author zzy
 * @date 2018/8/13
 */
@ScActionAnnotation(ActionConstants.ENTRY_MESSAGE_ACTIVITY_ACTION)
public class EntryMessageActivityAction implements ScAction {

    @Override
    public void invoke(Context context, final Bundle bundle, String s, final ScCallback scCallback) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, MessageActivity.class);
        context.startActivity(intent);
        if (scCallback != null) {
            scCallback.onCallback(true, new Bundle(), "");
        }
    }
}
