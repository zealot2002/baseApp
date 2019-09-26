package com.zzy.share.action;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.servercentre.ActionConstants;
import com.zzy.share.ShareSdkProxy;

/**
 * Created by haoran on 2018/11/16.
 */

@ScActionAnnotation(value = ActionConstants.INIT_SHARE_ACTION)
public class ShareInitAction implements ScAction {

    private static final String TAG = "ShareInitAction";

    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {
        Log.e(TAG,"start");
        // QQ 微信 微博
        ShareSdkProxy.getInstance().init(context, new String[]{"1104675129", "wx0c47eeb22d93e905", "1550938859"});
        Log.e(TAG,"end");
        if (scCallback!=null)
            scCallback.onCallback(true,null,"");
    }

}
