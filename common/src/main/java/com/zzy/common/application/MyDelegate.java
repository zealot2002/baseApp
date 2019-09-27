package com.zzy.common.application;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;

import com.evernote.android.job.JobManager;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zzy.common.BuildConfig;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.job.AJobCreator;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.CommonLibHelper;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ProcessUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

public class MyDelegate implements ApplicationDelegate {

    @Override
    public void onCreate(final Application application) {
        MyLog.e("onCreate");
        /* server centre */
        SCM.getInstance().init(application);
        /* sp content provider */
        SPHelper.init(application);
        /*common lib*/
        CommonLibHelper.init(application);
        /* auto layout */
        AutoLayoutConifg.getInstance().useDeviceSize().init(application);
        /* log */
//        MyLog.init(BuildConfig.DEBUG,application,"dk");
        MyLog.init(true,application,"dk");

        ToastUtils.setGravity(Gravity.CENTER, 0, 0);

        AppUtils.addOnAppStatusChangedListener(new AppUtils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                Log.e("zzy","onForeground");
            }

            @Override
            public void onBackground() {

            }
        });

        if (ProcessUtils.isMainProcess(application)) {
            /* job */
            JobManager.create(application).addJobCreator(new AJobCreator());
            //JPushInitJob.execute();
            // step counter

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
