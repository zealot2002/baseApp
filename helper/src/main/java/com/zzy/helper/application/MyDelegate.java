package com.zzy.helper.application;


import android.app.Application;

import com.zzy.common.application.ApplicationDelegate;
import com.zzy.commonlib.utils.ProcessUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

public class MyDelegate implements ApplicationDelegate {
    @Override
    public void onCreate(final Application application) {

        if (!ProcessUtils.isMainProcess(application))
            return;
        try {
            SCM.getInstance().req(application, ActionConstants.GET_GLOBAL_CONFIG_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
