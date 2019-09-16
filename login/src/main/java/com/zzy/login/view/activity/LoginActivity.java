package com.zzy.login.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.presenter.LoginPresenter;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

import java.util.List;

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
        questionPermissions();
    }

    private void questionPermissions() {
        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_PHONE_STATE})
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                ToastUtils.showShort("您必须授权才能使用app");
                etPhone.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }
        }).start();
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
                onSuccess();
//                try {
//                    SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                finish();
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
    public void onFirstSuccess(String s) {

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
