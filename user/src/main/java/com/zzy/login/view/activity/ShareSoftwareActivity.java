package com.zzy.login.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.user.R;

/**
 * 分享软件
 */
public class ShareSoftwareActivity extends BaseToolbarActivity implements View.OnClickListener {
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvToBePioneer,tvForgetPassword;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_school_fragment);
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    private void setupViews() {
//        etPhone = findViewById(R.id.etPhone);
//        etPassword = findViewById(R.id.etPassword);
//        btnOk = findViewById(R.id.btnOk);
//        tvToBePioneer = findViewById(R.id.tvToBePioneer);
//        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        btnOk.setOnClickListener(this);
        tvToBePioneer.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btnOk){
//            // TODO: 2019/8/19   to login
//        }else if(v.getId() == R.id.tvToBePioneer){
//
//        }else if(v.getId() == R.id.tvForgetPassword){
//
//        }

    }
}
