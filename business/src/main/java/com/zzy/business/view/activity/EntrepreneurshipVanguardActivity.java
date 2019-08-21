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
 * 创业先锋
 */
public class EntrepreneurshipVanguardActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvToBePioneer,tvForgetPassword;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业先锋");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_entrepreneurship_vanguard_activity;
    }

    private void setupViews() {
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnOk = findViewById(R.id.btnOk);
        tvToBePioneer = findViewById(R.id.tvToBePioneer);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        btnOk.setOnClickListener(this);
        tvToBePioneer.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            // TODO: 2019/8/19   to login 
        }else if(v.getId() == R.id.tvToBePioneer){

        }else if(v.getId() == R.id.tvForgetPassword){

        }

    }
}
