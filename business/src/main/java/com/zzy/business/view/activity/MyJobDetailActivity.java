package com.zzy.business.view.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.MyJobContract;
import com.zzy.business.presenter.MyJobPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.Job;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 招聘信息详情
 */
public class MyJobDetailActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener,MyJobContract.View {
    private EditText etCompanyName,etJobName,etAddress,etHeadcount,
            etSalaryMin,etSalaryMax,etPhone,etContact,etJobContent,etJobRequirements;
    private Button btnUpdate,btnStop;

    private int id;
    private MyJobContract.Presenter presenter;
    private Job bean;
    private List<String> educationList;
    private MaterialSpinner spinnerEducation;
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

    private void repairEducationList() {

        educationList = new ArrayList<>();
        educationList.add("学历不限");
        educationList.add("小学");
        educationList.add("初中");
        educationList.add("中专");
        educationList.add("高中");
        educationList.add("大专");
        educationList.add("本科");
        educationList.add("研究生");
        educationList.add("硕士");
        educationList.add("博士");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, educationList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerEducation = findViewById(R.id.spinnerEducation);
        spinnerEducation.setAdapter(adapter);
        spinnerEducation.setSelection(getEducationPosition());
    }

    private int getEducationPosition() {
        for(int i=0;i<educationList.size();i++){
            if(!TextUtils.isEmpty(bean.getEducation())
                &&bean.getEducation().equals(educationList.get(i))){
                return i;
            }
        }
        return 0;
    }

    private void setupViews() {
        etCompanyName = findViewById(R.id.etCompanyName);
        etJobName = findViewById(R.id.etJobName);
        etAddress = findViewById(R.id.etAddress);
        etHeadcount = findViewById(R.id.etHeadcount);

        etSalaryMin = findViewById(R.id.etSalaryMin);
        etSalaryMax = findViewById(R.id.etSalaryMax);
        etPhone = findViewById(R.id.etPhone);
        etContact = findViewById(R.id.etContact);
        etJobContent = findViewById(R.id.etJobContent);
        etJobRequirements = findViewById(R.id.etJobRequirements);
        spinnerEducation = findViewById(R.id.spinnerEducation);
        repairEducationList();


        etHeadcount.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );

        etCompanyName.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );
        etJobName.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );
        etAddress.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );
        etContact.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );
        etJobContent.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(500)}
        );
        etJobRequirements.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(500)}
        );


        etCompanyName.setText(bean.getCompanyName());
        etJobName.setText(bean.getJobName());
        etAddress.setText(bean.getAddress());
        etHeadcount.setText(bean.getHeadcount());
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

        if(!TextUtils.isEmpty(bean.getState())
                &&bean.getState().equals("完成")){
            btnUpdate.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);
        }
    }
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Job) o;
            bean.setId(id);
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdate){
            bean.setCompanyName(etCompanyName.getText().toString().trim());
            bean.setJobName(etJobName.getText().toString().trim());
            bean.setAddress(etAddress.getText().toString().trim());
            bean.setHeadcount(etHeadcount.getText().toString().trim());
            bean.setEducation(educationList.get(spinnerEducation.getSelectedItemPosition()));
            bean.setSalaryMax(etSalaryMax.getText().toString().trim());
            bean.setSalaryMin(etSalaryMin.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setContact(etContact.getText().toString().trim());
            bean.setFrom(etAddress.getText().toString().trim());
            bean.setJobContent(etJobContent.getText().toString().trim());
            bean.setJobRequirements(etJobRequirements.getText().toString().trim());

            if(TextUtils.isEmpty(bean.getCompanyName())){
                ToastUtils.showShort("请填写单位名称");
                return;
            }
            if(TextUtils.isEmpty(bean.getJobName())){
                ToastUtils.showShort("请填写岗位名称");
                return;
            }
            if(TextUtils.isEmpty(bean.getAddress())){
                ToastUtils.showShort("请填写工作地址");
                return;
            }
            if(TextUtils.isEmpty(bean.getEducation())){
                ToastUtils.showShort("请填写学历要求");
                return;
            }
            if(TextUtils.isEmpty(bean.getSalaryMax())
                    ||TextUtils.isEmpty(bean.getSalaryMin())
            ){
                ToastUtils.showShort("请填写职位薪资");
                return;
            }
            if(TextUtils.isEmpty(bean.getPhone())){
                ToastUtils.showShort("请填写联系电话");
                return;
            }
            if(TextUtils.isEmpty(bean.getContact())){
                ToastUtils.showShort("请填写联系人");
                return;
            }
            if(TextUtils.isEmpty(bean.getJobContent())){
                ToastUtils.showShort("请填写工作要求");
                return;
            }
            if(TextUtils.isEmpty(bean.getJobRequirements())){
                ToastUtils.showShort("请填写岗位要求");
                return;
            }
            if(Float.valueOf(bean.getSalaryMin())>Float.valueOf(bean.getSalaryMax())){
                ToastUtils.showShort("起始薪资不得大于最高薪资");
                return;
            }
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
        finish();
    }
}
