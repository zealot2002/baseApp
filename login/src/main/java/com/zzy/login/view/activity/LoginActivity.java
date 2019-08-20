package com.zzy.login.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.login.R;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

/**
 * login
 */
public class LoginActivity extends BaseAppActivity implements View.OnClickListener {
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvToBePioneer,tvForgetPassword;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        setupViews();
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
            // TODO: 2019/8/20  to login!
            try {
                SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }else if(v.getId() == R.id.tvToBePioneer){
            startActivity(RegisterActivity.class);
        }else if(v.getId() == R.id.tvForgetPassword){
            startActivity(ForgetPasswordActivity.class);
        }

    }
}
