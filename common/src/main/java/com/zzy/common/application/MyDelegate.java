package com.zzy.common.application;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;

import com.evernote.android.job.JobManager;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.job.AJobCreator;
import com.zzy.common.job.ShareInitJob;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.CommonLibHelper;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ProcessUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.scm.ActionConstants;

public class MyDelegate implements ApplicationDelegate {

    @Override
    public void onCreate(final Application application) {
        MyLog.e("onCreate");
        /* sp content provider */
        SPHelper.init(application);
        /*common lib*/
        CommonLibHelper.init(application);
        /* server centre */
        SCM.getInstance().init(application);
        /* auto layout */
        AutoLayoutConifg.getInstance().useDeviceSize().init(application);
        /* log */
        MyLog.setLogEnable(true);

        ToastUtils.setGravity(Gravity.CENTER, 0, 0);

        AppUtils.addOnAppStatusChangedListener(new AppUtils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                Log.e("zzy","onForeground");
                String currentProcess = ProcessUtils.getCurrentProcessName(application);
                String lastProcess = SPHelper.getString(SPConstants.LAST_PROCESS,"");
                if(currentProcess.equals(lastProcess)){
                    long now = System.currentTimeMillis();
                    long lastTime = SPHelper.getLong(SPConstants.LAST_TIME, 0);
                    try {
                        if ((now - lastTime) > CommonUtils.getGesturesPwdTimerout()) {
                            SCM.getInstance().req(AppUtils.getTopActivityOrApp(),
                                    ActionConstants.ENTRY_GESTURES_PWD_ACTIVITY_ACTION);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onBackground() {
//                long lastTime = System.currentTimeMillis();
//                SPHelper.save(SPConstants.LAST_TIME, lastTime);
//                SPHelper.save(SPConstants.LAST_PROCESS,
//                        ProcessUtils.getCurrentProcessName(application));
            }
        });

        if (ProcessUtils.isMainProcess(application)) {
            /* job */
            JobManager.create(application).addJobCreator(new AJobCreator());
            //JPushInitJob.execute();
            ShareInitJob.execute();
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
