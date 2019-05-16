package com.zzy.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.scm.ActionConstants;


/**
 * @author zzy
 * @date 2018/8/7
 */

public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";
    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_welcome);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                judgeNextEntry();
            }
        }, 3000);
    }
    private void judgeNextEntry(){
        String guideLooker = SPHelper.getString(SPConstants.GUIDE_LOOKER,"");
        if(guideLooker.equals(AppUtils.getVersionName())){
            entryMain();
        }else{
            Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void entryMain() {
        try {
            SCM.getInstance().req(WelcomeActivity.this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION, new ScCallback() {
                @Override
                public void onCallback(boolean b, Bundle bundle, String s) {
                    if (b) {
                        // 成功打开Home页面之后，关闭WelcomeActivity
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }, 5000);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 500);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
