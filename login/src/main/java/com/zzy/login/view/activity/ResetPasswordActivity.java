package com.zzy.login.view.activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.presenter.LoginPresenter;

/**
 * 修改密码
 */
public class ResetPasswordActivity extends BaseAppActivity implements View.OnClickListener, LoginContract.View  {
    private EditText etOld,etPw1,etPw2;
    private Button btnOk;
    private LoginContract.Presenter presenter;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_password_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rootView));
        setupViews();
        presenter = new LoginPresenter(this);
    }

    private void setupViews() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("修改密码");
        RelativeLayout rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etOld = findViewById(R.id.etOld);
        etPw1 = findViewById(R.id.etPw1);
        etPw2 = findViewById(R.id.etPw2);
        btnOk = findViewById(R.id.btnOk);
//
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try{

            if(v.getId() == R.id.btnOk){
                if(!checkData()) {
                    return;
                }
                presenter.resetPw(
                        etOld.getText().toString().trim(),
                        etPw1.getText().toString().trim());
//                startActivity(LoginActivity.class);
//                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkData() {
        if(!etPw1.getText().toString().trim().equals(etPw2.getText().toString().trim())){
            ToastUtils.showShort("您设置的两次密码不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void showError(String s) {

    }

    @Override
    public void onFirstSuccess(String s) {

    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
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
