package com.zzy.share.view;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.zzy.share.ShareSdkProxy;

public interface IShareView {

    IShareView createShareDialog(Context context, int[] shareChannel, int spanCount);

    int show(FragmentTransaction transaction);

    void show(FragmentManager manager);

    void dismissDialog();

    void setOnShareClickListener(ShareSdkProxy.OnShareClickListener listener);

}
