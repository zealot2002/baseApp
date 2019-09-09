package com.zzy.user.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.glide.GlideCacheUtil;
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

        tvCacheSize.setText(GlideCacheUtil.getInstance().getCacheFormatSize(this));

        rlModifyPw.setOnClickListener(this);
        rlClearCache.setOnClickListener(this);
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void reload(boolean bShow) {

    }
}
