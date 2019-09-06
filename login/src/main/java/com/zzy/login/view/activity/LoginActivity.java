package com.zzy.login.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.presenter.LoginPresenter;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

/**
 * login
 */
public class LoginActivity extends BaseAppActivity implements View.OnClickListener, LoginContract.View {
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvRegister,tvForgetPassword;
    private LoginContract.Presenter presenter;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        setupViews();
        presenter = new LoginPresenter(this);
    }

    private void setupViews() {
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnOk = findViewById(R.id.btnOk);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        btnOk.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.btnOk){
                presenter.login(etPhone.getText().toString().trim(),etPassword.getText().toString().trim());

                try {
                    SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }else if(v.getId() == R.id.tvRegister){
                startActivity(RegisterActivity.class);
            }else if(v.getId() == R.id.tvForgetPassword){
                startActivity(ForgetPasswordActivity.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        try {
            SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showDisconnect() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void updateUI(Object o) {

    }
}
