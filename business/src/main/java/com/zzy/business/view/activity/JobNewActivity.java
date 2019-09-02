package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.JobContract;
import com.zzy.business.model.bean.Job;
import com.zzy.business.presenter.JobPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.widget.MyEditText;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 我要招聘
 */
public class JobNewActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener , JobContract.View {
    private Job bean;
    private EditText etCompanyName,etJobName,etAddress,etHeadcount,etEducation,
            etSalaryMin,etSalaryMax,etPhone,etContact;
    private MyEditText etJobContent,etJobRequirements;
    private Button btnOk;
    private JobContract.Presenter presenter;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("我要招聘");
            bean = new Job();
            setupViews();
            presenter = new JobPresenter(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_job_new_activity;
    }

    private void setupViews() {
        etCompanyName = findViewById(R.id.etCompanyName);
        etJobName = findViewById(R.id.etJobName);
        etAddress = findViewById(R.id.etAddress);
        etHeadcount = findViewById(R.id.etHeadcount);
        etEducation = findViewById(R.id.etEducation);
        etSalaryMin = findViewById(R.id.etSalaryMin);
        etSalaryMax = findViewById(R.id.etSalaryMax);
        etPhone = findViewById(R.id.etPhone);
        etContact = findViewById(R.id.etContact);
        etJobContent = findViewById(R.id.etJobContent);
        etJobRequirements = findViewById(R.id.etJobRequirements);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean.setJobRequirements(etJobRequirements.getText().toString().trim());
            bean.setJobContent(etJobContent.getText().toString().trim());
            bean.setEducation(etEducation.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setContact(etContact.getText().toString().trim());
            bean.setAddress(etAddress.getText().toString().trim());
            bean.setHeadcount(etHeadcount.getText().toString().trim());
            bean.setCompanyName(etCompanyName.getText().toString().trim());
            bean.setJobName(etJobName.getText().toString().trim());
            bean.setSalaryMin(etSalaryMin.getText().toString().trim());
            bean.setSalaryMax(etSalaryMax.getText().toString().trim());

            presenter.newJob(bean);
        }
    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
        finish();
    }
}
