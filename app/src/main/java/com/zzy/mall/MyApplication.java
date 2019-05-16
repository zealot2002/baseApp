package com.zzy.mall;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.zzy.common.application.ApplicationDelegate;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.ProcessUtils;
import com.zzy.sc.core.utils.ClassUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/8/10
 */

public class MyApplication extends Application {
    private List<ApplicationDelegate> mAppDelegateList;

    /***************************************************************************************/
    @Override
    public void onCreate() {
        MyLog.e("onCreate");
//        if (ProcessUtils.isMainProcess(this))
//            HotfixAdapter.setStrictMode(BuildConfig.DEBUG);
        super.onCreate();

        // init跨进程库
        initMultiProcessCoreLib();

        // 非主进程，至此返回
        if (!ProcessUtils.isMainProcess(this)) return;

        // init主进程库
        initMainProcessCoreLib(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);

    }

    private void initMainProcessCoreLib(Application app) {

    }

    private void initMultiProcessCoreLib() {
        try {
            /*init modules*/
            mAppDelegateList = new ArrayList<>();
            mAppDelegateList = ClassUtils.getObjectsWithInterface(this,
                    ApplicationDelegate.class, "com.zzy");
            for (ApplicationDelegate delegate : mAppDelegateList) {
                delegate.onCreate(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }

}
