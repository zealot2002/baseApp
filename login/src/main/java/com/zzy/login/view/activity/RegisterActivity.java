package com.zzy.login.view.activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.commonlib.utils.ValidateUtils;
import com.zzy.login.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * register
 */
public class RegisterActivity extends BaseAppActivity implements View.OnClickListener {
    private EditText etName,etPhone,etSms,etInviter,etIdNo,etPd,etAddress;
    private Button btnNext,btnOk;
    private TextView tvSendSms,tvBirthday;
    private MaterialSpinner spinnerCounty,spinnerTown,spinnerVillage;
    private RelativeLayout rlBirthday;
    private TimePickerView pvTime;
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

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etSms = findViewById(R.id.etSms);
        etInviter = findViewById(R.id.etInviter);
        etIdNo = findViewById(R.id.etIdNo);
        etPd = findViewById(R.id.etPd);
        etAddress = findViewById(R.id.etAddress);

        tvSendSms = findViewById(R.id.tvSendSms);
        btnNext = findViewById(R.id.btnNext);
        tvBirthday = findViewById(R.id.tvBirthday);
        rlBirthday = findViewById(R.id.rlBirthday);

        rlBirthday.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvSendSms.setOnClickListener(this);

        setupSpinner();
    }

    private void setupSpinner() {
        List<String> list1 = new ArrayList<>();
        list1.add("青田县");
        list1.add("松阳县");
        list1.add("云和县");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerCounty = findViewById(R.id.spinnerCounty);
        spinnerCounty.setAdapter(adapter);

        List<String> list2 = new ArrayList<>();
        list2.add("颍川镇");
        list2.add("晋宁镇");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, list2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTown = findViewById(R.id.spinnerTown);
        spinnerTown.setAdapter(adapter2);

        List<String> list3 = new ArrayList<>();
        list3.add("大坑村");
        list3.add("祖父村");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item, list3);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerVillage = findViewById(R.id.spinnerVillage);
        spinnerVillage.setAdapter(adapter3);
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
        }else if(v.getId() == R.id.rlBirthday){
            //时间选择器
            if(pvTime == null){
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        tvBirthday.setText(cal.get(Calendar.YEAR)+"年"
                                +(1+cal.get(Calendar.MONTH))+"月"
                                +cal.get(Calendar.DAY_OF_MONTH)+"日");
                    }
                }).build();
            }
            pvTime.show();
        }
    }

    private boolean checkData() {
        if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())){
            ToastUtils.showShort("无效的手机号码");
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
