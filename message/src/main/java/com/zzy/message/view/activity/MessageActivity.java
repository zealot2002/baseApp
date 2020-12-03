package com.zzy.message.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.ActionConstants;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.message.R;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;

/**
 * @desc: 关于我们
 * @author: zhengxf
 * @created: 2018/12/4.
 */

public class MessageActivity extends BaseToolbarActivity implements View.OnClickListener {
    private LoadingHelper loadingDialog;
    TextView tvVersion;
    
    @Override
    protected int getLayoutId() {
        return R.layout.message_about_us_activity;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingHelper(this);
        initView();
    }
    
    private void initView() {
        setTitle(getResources().getString(R.string.other_about_us));
        setOnBackEventListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText("版本号：" + AppUtils.getVersionName());
        
        tvVersion.setOnClickListener(this);
        findViewById(R.id.rlPhone).setOnClickListener(this);
        findViewById(R.id.rlPrivateProtocol).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.tvVersion) {
                // 检查版本升级
                tvVersion.setEnabled(false);
                if(loadingDialog != null) {
                    loadingDialog.showLoading("加载中");
                }
                SCM.getInstance().req(this, ActionConstants.CHECK_UPDATE_ACTION, new ScCallback() {
                    @Override
                    public void onCallback(boolean b, Bundle data, String tag) {
                        if(loadingDialog != null) {
                            loadingDialog.closeLoading();
                            tvVersion.setEnabled(true);
                        }
                    }
                });
            }else if(v.getId() == R.id.rlPrivateProtocol) {
                Bundle bundle = new Bundle();
                bundle.putString(ParamConstants.URL, "http://www.sohu.com");
                SCM.getInstance().req(getApplicationContext(), ActionConstants.ENTRY_WEB_VIEW_ACTIVITY_ACTION, bundle);
            }
            else if(v.getId() == R.id.rlPhone) {
                BusHelper.getBus().post(BusConstants.EVENT_LOGIN_SUCCESS, "");
                ToastUtils.showShort("首页需要刷新");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadingDialog != null) {
            loadingDialog.closeLoading();
            loadingDialog = null;
        }
    }
}
