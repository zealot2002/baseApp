package com.zzy.user.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.model.bean.Archives;
import com.zzy.user.R;
import com.zzy.user.contract.MyArchivesContract;
import com.zzy.user.presenter.MyArchivesPresenter;

/**
 * 我的档案
 */
public class MyArchivesActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , MyArchivesContract.View {
    private Button btnModify;
    private TextView tvName,tvPhone,tvArea1,tvArea2,tvArea3,tvAddress,tvBirthday,tvSex,tvIdNo,tvUserType,tvIsCompany;
    private MyArchivesContract.Presenter presenter;
    private Archives bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的档案");
        presenter = new MyArchivesPresenter(this);
        presenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_archives_activity;
    }

    private void setupViews() {
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvArea1 = findViewById(R.id.tvArea1);
        tvArea2 = findViewById(R.id.tvArea2);
        tvArea3 = findViewById(R.id.tvArea3);
        tvAddress = findViewById(R.id.tvAddress);
        tvBirthday = findViewById(R.id.tvBirthday);
        tvSex = findViewById(R.id.tvSex);
        tvIdNo = findViewById(R.id.tvIdNo);
        tvUserType = findViewById(R.id.tvUserType);
        tvIsCompany = findViewById(R.id.tvIsCompany);

        btnModify = findViewById(R.id.btnModify);
        btnModify.setOnClickListener(this);

        tvName.setText(bean.getName());
        tvPhone.setText(bean.getPhone());
        tvArea1.setText(bean.getArea1());
        tvArea2.setText(bean.getArea2());
        tvArea3.setText(bean.getArea3());
        tvAddress.setText(bean.getAddress());
        tvBirthday.setText(bean.getBirthday());
        tvSex.setText(bean.getSex());
        tvIdNo.setText(bean.getIdNo());
        tvUserType.setText(bean.getUserType());
        tvIsCompany.setText(bean.getIsCompany());
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Archives) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnModify){
            // TODO: 2019/8/19   to login
        }

    }

    @Override
    public void reload(boolean bShow) {
        presenter.start();
    }
}
