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
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.commonlib.utils.ValidateUtils;
import com.zzy.login.R;

/**
 * register
 */
public class RegisterActivity extends BaseAppActivity implements View.OnClickListener {
    private EditText etPhone,etSms,etPd1,etPd2;
    private Button btnNext,btnOk;
    private TextView tvSendSms;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rootView));
        setupViews();
    }

    private void setupViews() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("创业之旅");
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
        btnNext = findViewById(R.id.btnNext);
//
        btnNext.setOnClickListener(this);
        tvSendSms.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNext){
            if(!checkData()) {
                return;
            }
            // TODO: 2019/8/19   to reset pd
            startActivity(LoginActivity.class);
            finish();
        }else if(v.getId() == R.id.tvSendSms){
            if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())) {
                ToastUtils.showShort("无效的手机号码");
                return;
            }
            // TODO: 2019/8/19   to get sms
            cdTimer.start();
            tvSendSms.setOnClickListener(null);
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
            tvSendSms.setOnClickListener(RegisterActivity.this);
        }
    };
}
