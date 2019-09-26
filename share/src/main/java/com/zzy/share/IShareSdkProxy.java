package com.zzy.share;

import android.app.Activity;
import android.content.Context;

import com.zzy.share.model.ShareBean;
import com.zzy.share.view.IShareView;

/**
 * Created by haoran on 2018/8/8.
 */

public interface IShareSdkProxy {

    void init(Context app, String[] appIds);

    IShareView createShareDialog(Context context, int[] shareChannel, int column);

    void setOnShareClickListener(IShareView dialog, final Activity activity, final ShareBean shareBean);

}
