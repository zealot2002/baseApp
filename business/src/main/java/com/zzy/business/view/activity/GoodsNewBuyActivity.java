package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;

/**
 * 我要买东西
 */
public class GoodsNewBuyActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etPhone,etPassword;
    private Button btnOk;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_new_buy_activity;
    }

    private void setupViews() {
        setTitle("我要买东西");
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            // TODO: 2019/8/19   to login
        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
