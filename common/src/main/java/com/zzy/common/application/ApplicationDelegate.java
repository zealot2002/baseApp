package com.zzy.common.application;


import android.app.Application;

public interface ApplicationDelegate {

    void onCreate(Application application);

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
