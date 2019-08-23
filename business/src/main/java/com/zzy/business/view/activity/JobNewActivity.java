package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Jd;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.MyEditText;
import com.zzy.common.widget.PopupEditDialog;

/**
 * 我要招聘
 */
public class JobNewActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Jd bean;
    private EditText etCompanyName,etJobName,etAddress,etHeadcount,etEducation,
            etSalary,etPhone,etContact;
    private MyEditText etJobContent,etJobRequirements;
    private TextView tvReport;
    private PopupEditDialog dialog;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("我要招聘");
            bean = new Jd();
            setupViews();
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
        etSalary = findViewById(R.id.etSalary);
        etPhone = findViewById(R.id.etPhone);
        etContact = findViewById(R.id.etContact);
        etJobContent = findViewById(R.id.etJobContent);
        etJobRequirements = findViewById(R.id.etJobRequirements);

        etCompanyName.setText(bean.getCompanyName());
        etJobName.setText(bean.getJobName());
        etAddress.setText(bean.getAddress());
        etHeadcount.setText(bean.getHeadcount());
        etEducation.setText(bean.getEducation());
        etSalary.setText(bean.getSalary());
        etPhone.setText(bean.getPhone());
        etContact.setText(bean.getContact());
        etJobContent.setText(bean.getJobContent());
        etJobRequirements.setText(bean.getJobRequirements());

        tvReport = findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvReport){
            if(dialog == null){
                dialog = new PopupEditDialog.Builder(this, "举报原因：","完成",
                        new PopupEditDialog.OnClickOkListener() {
                            @Override
                            public void clickOk(String content) {
                                // TODO: 2019/8/23  report content
                                dialog.dismiss();
                            }
                        }
                ).create();
            }
            dialog.show();
        }
    }
}
