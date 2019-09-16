package com.zzy.user.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.glide.GlideCacheUtil;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.User;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.SPUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;
import com.zzy.user.R;

/**
 * 系统设置
 */
public class SettingsActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private RelativeLayout rlModifyPw,rlClearCache;
    private TextView tvCacheSize;
    private Button btnLogout;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统设置");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_settings_activity;
    }

    private void setupViews() {
        rlModifyPw = findViewById(R.id.rlModifyPw);
        rlClearCache = findViewById(R.id.rlClearCache);
        tvCacheSize = findViewById(R.id.tvCacheSize);
        btnLogout = findViewById(R.id.btnLogout);

        tvCacheSize.setText(GlideCacheUtil.getInstance().getCacheFormatSize(this));

        rlModifyPw.setOnClickListener(this);
        rlClearCache.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.rlModifyPw){
                SCM.getInstance().req(this, ActionConstants.ENTRY_RESET_PW_ACTIVITY_ACTION);
            }else if(v.getId() == R.id.rlClearCache){
                if(GlideCacheUtil.getInstance().getCacheFloatSize(this)>0){
                    ToastUtils.showShort("清理完成");
                    tvCacheSize.setText("0MB");
                    GlideCacheUtil.getInstance().clearImageDiskCache(this);
                }
            }else if(v.getId() == R.id.btnLogout){
                doLogout();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doLogout() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.logout(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        //clear token and userId
                        SPUtils.putString(AppUtils.getApp(), SPConstants.TOKEN,"");
                        SPUtils.putString(AppUtils.getApp(), SPConstants.USER_ID,"");
                        ToastUtils.showShort("成功");
                        try {
                            SCM.getInstance().req(SettingsActivity.this,
                                    ActionConstants.ENTRY_LOGIN_ACTIVITY_ACTION);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        ToastUtils.showShort((String) o);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void reload(boolean bShow) {

    }
}
