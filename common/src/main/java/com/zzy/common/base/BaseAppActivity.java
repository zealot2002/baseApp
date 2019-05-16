package com.zzy.common.base;

import android.content.Intent;
import android.os.Bundle;

import com.zzy.commonlib.base.BaseActivity;


public abstract class BaseAppActivity extends BaseActivity {


    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
