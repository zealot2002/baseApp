package com.zzy.user.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.Job;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.user.R;
import com.zzy.user.contract.MyJobContract;
import com.zzy.user.presenter.MyJobPresenter;

/**
 * 招聘信息详情
 */
public class MyJobDetailActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener,MyJobContract.View {
    private EditText etCompanyName,etJobName,etAddress,etHeadcount,etEducation,
            etSalaryMin,etSalaryMax,etPhone,etContact,etJobContent,etJobRequirements;
    private TextView tvReport;
    private Button btnUpdate,btnStop;

    private int id;
    private MyJobContract.Presenter presenter;
    private Job bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("招聘信息详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new MyJobPresenter(this);
            presenter.getDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_my_job_detail_activity;
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

        etCompanyName.setText(bean.getCompanyName());
        etJobName.setText(bean.getJobName());
        etAddress.setText(bean.getAddress());
        etHeadcount.setText(bean.getHeadcount());
        etEducation.setText(bean.getEducation());
        etSalaryMin.setText(bean.getSalaryMin());
        etSalaryMax.setText(bean.getSalaryMax());
        etPhone.setText(bean.getPhone());
        etContact.setText(bean.getContact());
        etJobContent.setText(bean.getJobContent());
        etJobRequirements.setText(bean.getJobRequirements());

        btnUpdate = findViewById(R.id.btnUpdate);
        btnStop = findViewById(R.id.btnStop);
        btnUpdate.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Job) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdate){
            presenter.update(bean);
        }else if(v.getId() == R.id.btnStop){
            presenter.stop(id);
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getDetail(id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
    }
}
