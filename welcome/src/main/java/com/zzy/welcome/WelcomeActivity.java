package com.zzy.welcome;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.SPConstants;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

/**
 * @author zzy
 * @date 2019/8/21
 */

public class WelcomeActivity extends BaseAppActivity {
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
        }, 2000);
    }
    private void judgeNextEntry(){
        try{
            String token = SPHelper.getString(SPConstants.TOKEN,"");
            if(TextUtils.isEmpty(token)){
                SCM.getInstance().req(this, ActionConstants.ENTRY_LOGIN_ACTIVITY_ACTION);
            }else{
                SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
            }
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
