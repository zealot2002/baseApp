package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.business.contract.JobContract;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.model.bean.Job;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.business.presenter.JobPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 招聘信息详情
 */
public class JobDetailActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener,JobContract.View {
    private EditText etCompanyName,etJobName,etAddress,etHeadcount,etEducation,
            etSalaryMin,etSalaryMax,etPhone,etContact,etJobContent,etJobRequirements;
    private TextView tvReport;
    private PopupEditDialog dialog;

    private int id;
    private JobContract.Presenter presenter;
    private Job bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("招聘信息详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new JobPresenter(this);
            presenter.getJobDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_job_detail_activity;
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

        tvReport = findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);
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
        if(v.getId() == R.id.tvReport){
            if(dialog == null){
                dialog = new PopupEditDialog.Builder(this, "举报原因：","完成",
                        new PopupEditDialog.OnClickOkListener() {
                            @Override
                            public void clickOk(String content) {
                                if(content.isEmpty()){
                                    ToastUtils.showShort("内容不能为空");
                                    return;
                                }
                                presenter.report(id,content);
                                dialog.dismiss();
                            }
                        }
                ).create();
            }
            dialog.show();
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getJobDetail(id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("举报成功");
    }
}
