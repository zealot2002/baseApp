package com.zzy.share.action;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.servercentre.ActionConstants;
import com.zzy.share.ShareChannel;
import com.zzy.share.ShareSdkProxy;
import com.zzy.share.model.ShareBean;
import com.zzy.share.view.IShareView;

/**
 * Created by haoran on 2018/11/16.
 */

@ScActionAnnotation(ActionConstants.SHOW_SHARE_DIALOG_ACTION)
public class ShareShowAction implements ScAction {

    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {
        if (bundle==null){
            return;
        }
        IShareView shareDialog = ShareSdkProxy.getInstance().createShareDialog(context,
                new int[]{ShareChannel.CHANNEL_QQ, ShareChannel.CHANNEL_WECHAT_MOMENT,
                        ShareChannel.CHANNEL_WECHAT}, 3);
        Activity activity = (Activity) context;
        if (activity!=null){
            // 设置点击回调和数据
            ShareSdkProxy.getInstance().setOnShareClickListener(shareDialog, activity,
                    new ShareBean(bundle.getString("title"),
                            bundle.getString("content"),
                            bundle.getString("url"),
                            bundle.getInt("defaultIcon"),
                            bundle.getString("actionUrl")));
            // 展示 dialog
            shareDialog.show(activity.getFragmentManager());
        }
        if (scCallback!=null)
            scCallback.onCallback(true,null,"");

    }

}
