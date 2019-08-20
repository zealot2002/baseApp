package com.zzy.common.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BackToolBar;


/**
 */
public abstract class BaseTitleAndBottomBarActivity extends BaseAppActivity {
    private TextView tvTitle;
    private RelativeLayout rlBack;
    /****************************************************************************************************/
    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_title_and_bottom_bar_container);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rlRoot));
        View view = View.inflate(this, getLayoutId(), null);
        getContainer().addView(view);

        tvTitle = findViewById(R.id.tvTitle);
        rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }

    protected ViewGroup getContainer() {
        return findViewById(R.id.container);
    }

    protected abstract int getLayoutId();


}
