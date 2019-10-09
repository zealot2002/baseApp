package com.zzy.login.view.activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.commonlib.utils.ValidateUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.presenter.LoginPresenter;

import java.util.List;

/**
 * forget password
 */
public class ForgetPasswordActivity extends BaseAppActivity implements View.OnClickListener, LoginContract.View  {
    private EditText etPhone,etSms,etPd1,etPd2;
    private Button btnOk;
    private TextView tvSendSms;
    private LoginContract.Presenter presenter;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forget_password_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rootView));
        setupViews();
        presenter = new LoginPresenter(this);
    }

    private void setupViews() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("忘记密码");
        RelativeLayout rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etPhone = findViewById(R.id.etPhone);
        etSms = findViewById(R.id.etSms);
        etPd1 = findViewById(R.id.etPd1);
        etPd2 = findViewById(R.id.etPd2);
        tvSendSms = findViewById(R.id.tvSendSms);
        btnOk = findViewById(R.id.btnOk);
//
        btnOk.setOnClickListener(this);
        tvSendSms.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.btnOk){
                if(!checkData()) {
                    return;
                }
                presenter.forgetPw(
                        etPhone.getText().toString().trim(),
                        etPd2.getText().toString().trim(),
                        etSms.getText().toString().trim());
//                startActivity(LoginActivity.class);
//                finish();
            }else if(v.getId() == R.id.tvSendSms){
                if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("无效的手机号码");
                    return;
                }
                presenter.getSms(etPhone.getText().toString().trim());
                cdTimer.start();
                tvSendSms.setOnClickListener(null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkData() {
        if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())){
            ToastUtils.showShort("无效的手机号码");
            return false;
        }
        if(!etPd1.getText().toString().trim().equals(etPd2.getText().toString().trim())){
            ToastUtils.showShort("您设置的两次密码不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cdTimer!=null){
            cdTimer.cancel();
            cdTimer = null;
        }
    }

    private CountDownTimer cdTimer = new CountDownTimer(30*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvSendSms.setText((millisUntilFinished / 1000) + " s");
        }

        @Override
        public void onFinish() {
            tvSendSms.setText("发送验证码");
            tvSendSms.setOnClickListener(ForgetPasswordActivity.this);
        }
    };

    @Override
    public void showError(String s) {

    }

    @Override
    public void onFirstSuccess(String s) {

    }

    @Override
    public void onTagList(List<String> tagList) {

    }

    @Override
    public void onSuccess() {
        startActivity(LoginActivity.class);
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
